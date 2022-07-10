package com.example.tourplanner.BuisnessLayer;

import com.example.tourplanner.BuisnessLayer.Event.EventManagerFactory;
import com.example.tourplanner.BuisnessLayer.Event.IEventListener;
import com.example.tourplanner.BuisnessLayer.Event.IEventManager;
import com.example.tourplanner.BuisnessLayer.Manager.PdfReportManager;
import com.example.tourplanner.BuisnessLayer.MapQuestAPI.MapQuest;
import com.example.tourplanner.DataAccessLayer.Factory.DataAccessFactory;
import com.example.tourplanner.DataAccessLayer.Interface.DAO.ITourDAO;
import com.example.tourplanner.DataAccessLayer.Interface.DAO.ITourLogDAO;
import com.example.tourplanner.DataAccessLayer.Interface.IFileActivity;
import com.example.tourplanner.MVVM.Model.Tour;
import com.example.tourplanner.MVVM.Model.TourLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuisnessLogic implements IEventListener,IBuisnessLogic{

    private final Logger log = LogManager.getLogger(BuisnessLogic.class);
    private final IEventManager em = EventManagerFactory.getEM();

    public BuisnessLogic() {
        em.subscribe("addTour", this);
        em.subscribe("editTour", this);
        em.subscribe("deleteTour", this);
    }

    @Override
    public String addTour(Tour t) {
        log.info("addTour");
        log.info("Tour ID:" + t.getId());

        ITourDAO tourDAO = DataAccessFactory.getTourDao();
        try {
            ArrayList<String> data = getTourDetails(t);
            float distance = Float.parseFloat(data.get(0));
            t.setDist(distance);
            t.setEstTime(data.get(1));
            String routeMap = getTourMap(t);
            t.setInfo(routeMap);
            if(tourDAO != null)
                return tourDAO.insertTour(t);
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    @Override
    public String addTourLog(Tour t, TourLog tl) {
        return null;
    }

    @Override
    public List<Tour> getAllTours() {
        log.info("getting tours");
        ITourDAO tourDAO = DataAccessFactory.getTourDao();
        try {
            if(tourDAO != null)
                return tourDAO.selectAllTours();
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    @Override
    public List<TourLog> getAllTourLogs() {
        log.info("Get all tour logs");
        ITourLogDAO tourLogDAO = DataAccessFactory.getTourLogDao();
        try {
            if(tourLogDAO != null)
                return tourLogDAO.selectAllTourLogs();
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    @Override
    public Tour getTourById(String id) {
        log.info("selecting tour by ID");
        log.info("Tour ID:"+id);
        ITourDAO tourDAO = DataAccessFactory.getTourDao();

        try {
            Tour tour = tourDAO.findById(id);
            if(tour != null)
                return tour;

        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    @Override
    public List<TourLog> getLogOfTour(Tour t) {
        log.info("Get logs of tour");
        log.info("Tour ID: "+ t.getId());
        ITourLogDAO tourLogDAO = DataAccessFactory.getTourLogDao();
        try {
            if(tourLogDAO != null)
                return tourLogDAO.selectLogOfTour(t);
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    @Override
    public File getTourImage(String name) {
        log.info("getting map image");
        log.info("map name: " + name);
        IFileActivity fileAccess = DataAccessFactory.getFileActivity();
        return fileAccess.readFile(name);
    }

    @Override
    public boolean updateTour(Tour t) {
        log.info("updating tour");
        log.info("Tour ID: " + t.getId());
        ITourDAO tourDAO = DataAccessFactory.getTourDao();
        String tourId = t.getId();
        ArrayList<String> data = getTourDetails(t);

        float distance = Float.parseFloat(data.get(0));
        t.setDist(distance);
        t.setEstTime(data.get(1));
        String routeMap = getTourMap(t);
        t.setInfo(routeMap);
        try {
            if(tourDAO != null)
                return tourDAO.updateTour(tourId, t);
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return false;
    }

    @Override
    public boolean updateTourLog(TourLog tl) {
        log.info("updating tour log");
        log.info("TourLog ID: " + tl.getId());
        ITourLogDAO tourLogDAO = DataAccessFactory.getTourLogDao();
        try {
            if(tourLogDAO != null)
                return tourLogDAO.updateTourLog(tl.getId(), tl);;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteTourById(String id) {
        log.info("deleting tour");
        log.info("tour ID: " + id);
        ITourDAO tourDAO = DataAccessFactory.getTourDao();
        try {
            if(tourDAO != null)
                return tourDAO.deleteTour(id);
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return false;
    }

    @Override
    public boolean deleteTourLogById(String id) {
        log.info("deleting tour log");
        log.info("TourLog ID: " + id);
        ITourLogDAO tourLogDAO = DataAccessFactory.getTourLogDao();
        try {
            if(tourLogDAO != null)
                return tourLogDAO.deleteTourLog(id);
        } catch(SQLException e) {
            e.printStackTrace();
            log.error(e);
        }
        return false;
    }

    @Override
    public boolean deleteTourImageByName(String name) {
        log.info("Deleting map image by name");
        log.info("Map image name: " + name);
        IFileActivity fileAccess = DataAccessFactory.getFileActivity();
        boolean result = fileAccess.deleteFile(name);
        if(result)
            return true;
        else {
            log.error("Deleting map image named " + name +"failed ");
            return false;
        }
    }

    @Override
    public String getTourMap(Tour t) {
        log.info("getting tour map");
        log.info("tour ID: " + t.getId());
        ArrayList<String> data = getTourDetails(t);

        if(Float.parseFloat(data.get(0)) > 0.0f)  {
            byte[] mapBytes = MapQuest.getMqApiMapImg(t.getStartDest(), t.getEndDest(), t.getTransportType());
            IFileActivity fileAccess = DataAccessFactory.getFileActivity();
            String imgPath = fileAccess.createFile(t.getId().toString() + ".jpg", mapBytes);

            if(imgPath != null) {
                return imgPath;
            }
            log.error("getting tour map failed");
            return null;
        }
        log.error("invalid data from MapQuest");
        return null;

    }

    @Override
    public ArrayList<String> getTourDetails(Tour t) {
        log.info("getting tour data");
        log.info("tour ID: " + t.getId());
        System.out.println(t.getStartDest());
        System.out.println(t.getEndDest());
        Map<String, Object> data = MapQuest.getMqApiRouteDirections(t.getStartDest(), t.getEndDest(), t.getTransportType());
        String distance = data.get("distance").toString();
        String estTime = data.get("formattedTime").toString();
        ArrayList<String> det = new ArrayList<>();
        det.add(distance);
        det.add(estTime);

        if(det != null)
            return det;

        log.error("getting tour data failed");
        return null;
    }

    @Override
    public void generateTourReport(Tour t, List<TourLog> tls) {
        log.info("generate tour report");
        log.info("tour ID: "+t.getId());
        PdfReportManager pdfManager = new PdfReportManager();
        pdfManager.generateTourReport(t, tls);
    }

    @Override
    public void generateSummaryReport(List<Tour> ts, List<TourLog> tls) {
        log.info("generate summary report");
        PdfReportManager pdfManager = new PdfReportManager();
        pdfManager.generateSummaryReport(ts, tls);
    }

    @Override
    public void update(String e, Object o) {}
}
