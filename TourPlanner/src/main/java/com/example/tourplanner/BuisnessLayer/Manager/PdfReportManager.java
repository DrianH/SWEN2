package com.example.tourplanner.BuisnessLayer.Manager;

import com.example.tourplanner.MVVM.Model.Tour;
import com.example.tourplanner.MVVM.Model.TourLog;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PdfReportManager {
    private String mapsPath;
    private String pdfReportPath;
    
    public PdfReportManager() {
        this.mapsPath = Paths.get("src", 
                                "main", 
                                        "resources", 
                                        "images").toFile().getAbsolutePath();
        this.pdfReportPath = Paths.get("pdfReports").toFile().getAbsolutePath();
    }

    private static LocalTime calcSumOfDurations(ArrayList<LocalTime> times) {
        String tmp = "00:00";
        LocalTime tmpLt = LocalTime.parse(tmp);
        long sum = tmpLt.toNanoOfDay();

        for(LocalTime t : times) {
            sum += t.toNanoOfDay();
        }
        return LocalTime.ofNanoOfDay(sum);
    }

    public void generateTourReport(Tour t, List<TourLog> tls) {
        pdfReportPath += "/" + t.getId() + ".pdf";

        try {
            PdfWriter pdfWriter = new PdfWriter(pdfReportPath);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            
            Paragraph header = new Paragraph("Tour: "+t.getId()+"\n")
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER_BOLD))
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.GREEN);
            document.add(header);

            Paragraph name = new Paragraph("Name: " + t.getTourName())
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(name);

            Paragraph from = new Paragraph("Start Destination: " + t.getStartDest())
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(from);

            Paragraph to = new Paragraph("End Destination: " + t.getEndDest())
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(to);

            Paragraph transportType = new Paragraph("Transport Type: " + t.getTransportType())
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(transportType);

            Paragraph distance = new Paragraph("Distance: " + t.getDist() + "km")
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(distance);

            Paragraph estTime = new Paragraph("Estimated Time: " + t.getEstTime())
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(estTime);

            Paragraph description = new Paragraph("Tour Description:" + t.getDescription())
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(description);
            
            String mapName = "/" + t.getId() + ".jpg";
            String mapPath = mapsPath + mapName;
            File tourMap = new File(mapPath);

            if(tourMap.exists()) {
                mapsPath += mapName;
            }

            Paragraph imageHeader = new Paragraph("\nMap:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(imageHeader);

            ImageData pathOfImage = ImageDataFactory.create(mapsPath);
            Image image = new Image(pathOfImage);
            image.setWidth(480);
            image.setHeight(240);
            document.add(image);
            document.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void generateSummaryReport(List<Tour> allTours, List<TourLog> allTourLogs) {
        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();

        String ldStr = ld.toString();
        long ltLong = lt.toNanoOfDay();
        String ltStr = String.valueOf(ltLong);

        pdfReportPath += "/__" + ldStr + "+" + ltStr + ".pdf";
        float totalDistance = 0.0f;
        ArrayList<LocalTime> allTourDurations = new ArrayList<>();

        try {
            PdfWriter pdfWriter = new PdfWriter(pdfReportPath);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            Paragraph header = new Paragraph("ldStr + "+" + ltStr\n")
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER_BOLD))
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontColor(ColorConstants.GREEN);
            document.add(header);

            Paragraph tourList = new Paragraph("All tours:")
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(tourList);

            Paragraph totalDistancesAmount = new Paragraph("- Total Distances: " + totalDistance + "km")
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(totalDistancesAmount);

            LocalTime totalTime = calcSumOfDurations(allTourDurations);
            Paragraph totalDurationAmount = new Paragraph("- Total Duration: " + totalTime)
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setFontSize(12)
                    .setFontColor(ColorConstants.BLACK);
            document.add(totalDurationAmount);
            document.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}

