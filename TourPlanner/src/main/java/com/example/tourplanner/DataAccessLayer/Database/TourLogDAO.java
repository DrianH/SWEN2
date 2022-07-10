package com.example.tourplanner.DataAccessLayer.Database;

import com.example.tourplanner.DataAccessLayer.Factory.DataAccessFactory;
import com.example.tourplanner.DataAccessLayer.Interface.DAO.ITourDAO;
import com.example.tourplanner.DataAccessLayer.Interface.DAO.ITourLogDAO;
import com.example.tourplanner.DataAccessLayer.Interface.IDatabaseActivity;
import com.example.tourplanner.MVVM.Model.Tour;
import com.example.tourplanner.MVVM.Model.TourLog;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TourLogDAO implements ITourLogDAO {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private IDatabaseActivity db;
    private ITourDAO tourDAO;

    // get
    private final String SELECT_ALL = "SELECT * FROM public.\"TourLog\";";
    private final String FIND_BY_ID = "SELECT * FROM public.\"TourLog\" WHERE \"tourLogId\" = ?;";
    private final String SELECT_LOG_OF_TOUR = "SELECT * FROM public.\"TourLog\" WHERE \"tourId\" = ?;";

    // change
    private final String INSERT_TOUR_LOG = "INSERT INTO public.\"TourLog\" (\"id\", \"tourId\", \"date\", \"commentary\", \"totalTime\") VALUES (?,?,?,?,?);";
    private final String UPDATE_TOUR_LOG = "UPDATE public.\"TourLog\" SET \"date\" = ?, \"commentary\" = ?, \"totalTime\" = ? WHERE \"tourLogId\" = ?;";
    private final String DELETE_TOUR_LOG = "DELETE FROM public.\"TourLog\" WHERE \"id\" = ?;";

    public TourLogDAO() {
        this.db = DataAccessFactory.getDb();
        this.tourDAO = DataAccessFactory.getTourDao();
    }

    private List<TourLog> parseQueryData(List<Map<String, Object>> data) {
        List<TourLog> parsedQueryData = new ArrayList<>();

        for (Map<String, Object> d :
                data) {
            TourLog tmp = new TourLog(
                    (String) d.get("id"),
                    (Tour) d.get("tour"),
                    LocalDate.parse((String) d.get("date"), dtf),
                    (String) d.get("commentary"),
                    (String) d.get("totalTime")
            );
            parsedQueryData.add(tmp);
        }
        return parsedQueryData;
    }

    @Override
    public List<TourLog> selectAllTourLogs() throws SQLException {
        List<Map<String, Object>> rows = db.read(SELECT_ALL);
        List<TourLog> tls = parseQueryData(rows);
        return tls;
    }


    @Override
    public TourLog findById(String id) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        return null;
    }

    @Override
    public List<TourLog> selectLogOfTour(Tour t) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(t.getId());
        List<Map<String, Object>> rows = db.read(SELECT_LOG_OF_TOUR, params);
        List<TourLog> tmp = new ArrayList<>();

        for(Map<String, Object> row : rows) {
            TourLog tl = new TourLog(
                    (String) row.get("id"),
                    t,
                    LocalDate.parse((String) row.get("date"), dtf),
                    (String) row.get("commentary"),
                    (String) row.get("totalTime"));
            tmp.add(tl);
        }
        return tmp;
    }

    @Override
    public String insertTourLog(TourLog tl, Tour t) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(tl.getId());
        params.add(t.getId());
        params.add(tl.getDate());
        params.add(tl.getCommentary());
        params.add(tl.getTotalTime());
        return db.add(INSERT_TOUR_LOG, params);
    }

    @Override
    public boolean updateTourLog(String id, TourLog tl) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(tl.getDate());
        params.add(tl.getCommentary());
        params.add(tl.getTotalTime());
        params.add(id);

        return db.update(UPDATE_TOUR_LOG, params);
    }

    @Override
    public boolean deleteTourLog(String id) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        return db.delete(DELETE_TOUR_LOG, params);
    }
}
