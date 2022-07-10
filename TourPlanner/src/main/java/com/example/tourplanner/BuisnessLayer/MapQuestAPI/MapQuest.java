package com.example.tourplanner.BuisnessLayer.MapQuestAPI;

import com.example.tourplanner.BuisnessLayer.Manager.ConfigPropManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MapQuest {

    public static Map<String, Object> getMqApiRouteDirections(String from, String to, String transportType) {
        try {
            String baseUrl = ConfigPropManager.getConfigProp("mq-url1");
            String key = ConfigPropManager.getConfigProp("mq-key");
            String reqString = "?key=" + key
                    + "&from=" + from
                    + "&to=" + to
                    + "&transportMode=" + transportType;

            if (transportType.equals("WALKING")) {
                reqString += "&routeType=pedestrian";
            } else if (transportType.equals("BICYCLE")) {
                reqString += "&routeType=bicycle";
            } else if (transportType.equals("CAR") || transportType.equals("BUS")) {
                reqString += "&routeType=shortest";
            }
            HttpClient httpCl = HttpClient.newHttpClient();
            HttpRequest httpReq = HttpRequest.newBuilder().uri(URI.create(baseUrl +""+ reqString)).build();
            CompletableFuture<HttpResponse<String>> httpResp = httpCl.sendAsync(httpReq, HttpResponse.BodyHandlers.ofString());
            String body = httpResp.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
            ObjectMapper om = new ObjectMapper();
            JsonNode jn = om.readTree(body);
            Map<String, Object> route = new HashMap<>();
            try {
                route.put("distance", jn.get("route").get("distance").floatValue()); //HERE
                route.put("formattedTime", jn.get("route").get("formattedTime").textValue());
                route.put("session", jn.get("route").get("sessionId").textValue());

                // "ul" and "lr" from boundingBox:
                JsonNode ul = jn.get("route").get("boundingBox").get("ul");
                JsonNode lr = jn.get("route").get("boundingBox").get("lr");
                route.put("boundingBox", ul.get("lat").floatValue() + "," + ul.get("lng").floatValue() + "," + lr.get("lat").floatValue() + "," + lr.get("lng").floatValue());

                return route;
            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] getMqApiMapImg(String from, String to, String transportType) {
        String baseUrl = ConfigPropManager.getConfigProp("mq-url2");
        String key = ConfigPropManager.getConfigProp("mq-key");
        try {
            Map<String, Object> route = MapQuest.getMqApiRouteDirections(from, to, transportType);
            if(route != null) {
                String requestData = "?key=" + key
                        + "&size=960,480"
                        + "&defaultMarker=none"
                        + "&session=" + route.get("session")
                        + "&boundingBox=" + route.get("boundingBox")
                        + "&to=" + to;
                HttpClient httpCl = HttpClient.newHttpClient();
                HttpRequest httpReq = HttpRequest.newBuilder().uri(URI.create(baseUrl + requestData)).build();
                HttpResponse<byte[]> httpResp = httpCl.send(httpReq, HttpResponse.BodyHandlers.ofByteArray());
                return httpResp.body();
            }
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
