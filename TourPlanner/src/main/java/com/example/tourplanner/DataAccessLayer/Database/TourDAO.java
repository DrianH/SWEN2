package com.example.tourplanner.DataAccessLayer.Database;

import com.example.tourplanner.DataAccessLayer.Factory.DataAccessFactory;
import com.example.tourplanner.DataAccessLayer.Interface.DAO.ITourDAO;
import com.example.tourplanner.DataAccessLayer.Interface.IDatabaseActivity;
import com.example.tourplanner.MVVM.Model.Tour;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TourDAO implements ITourDAO {

    private IDatabaseActivity db;

    // get
    private final String SELECT_ALL = "SELECT * FROM public.\"Tour\";";
    private final String FIND_BY_ID = "SELECT * FROM public.\"Tour\" WHERE \"id\" = ?;";

    // change
    private final String INSERT_TOUR = "INSERT INTO public.\"Tour\" (\"id\", \"tourName\", \"description\", \"startDest\", \"endDest\", \"transportType\", \"dist\", \"estTime\", \"info\") VALUES (?,?,?,?,?,?,CAST(? AS DECIMAL),?,?);";
    private final String UPDATE_TOUR = "UPDATE public.\"Tour\" SET \"tourName\" = ?, \"tourDesc\" = ?, \"startDest\" = ?, \"endDest\" = ?, \"transportType\" = ?, \"dist\" = CAST(? AS DECIMAL), \"estTime\" = ?, \"info\" = ? WHERE \"id\" = ?;";
    private final String DELETE_TOUR = "DELETE FROM public.\"Tour\" WHERE \"id\" = ?;";

    public TourDAO() {
        this.db = DataAccessFactory.getDb();
    }


    private List<Tour> parseQueryData(List<Map<String, Object>> data) {
        List<Tour> parsedData = new ArrayList<>();

        for(Map<String, Object> d : data) {
            Tour tmp = new Tour(
                    (String) d.get("id"),
                    (String) d.get("tourName"),
                    (String) d.get("description"),
                    (String) d.get("startDest"),
                    (String) d.get("endDest"),
                    (String) d.get("transportType"),
                    ((float) d.get("dist")),
                    (String) d.get("estTime"),
                    (String) d.get("info"));
            parsedData.add(tmp);
        }

        return parsedData;
    }

    @Override
    public List<Tour> selectAllTours() throws SQLException {
        List<Map<String, Object>> data = db.read(SELECT_ALL);
        List<Tour> ts = parseQueryData(data);

        return ts;
    }


    @Override
    public Tour findById(String id) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);
        List<Map<String, Object>> data = db.read(FIND_BY_ID, params);
        List<Tour> selTour = parseQueryData(data);

        if(selTour.size() > 0)
            return selTour.get(0);
        else
            return null;
    }

    @Override
    public String insertTour(Tour t) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();

        params.add(t.getId());
        params.add(t.getTourName());
        params.add(t.getDescription());
        params.add(t.getStartDest());
        params.add(t.getEndDest());
        params.add(t.getTransportType());
        params.add(t.getDist());
        params.add(t.getEstTime());
        params.add(t.getInfo());

        return db.add(INSERT_TOUR, params);
    }

    @Override
    public boolean updateTour(String id, Tour t) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();

        params.add(t.getTourName());
        params.add(t.getDescription());
        params.add(t.getStartDest());
        params.add(t.getEndDest());
        params.add(t.getTransportType());
        params.add(t.getDist());
        params.add(t.getEstTime());
        params.add(t.getInfo());
        params.add(id);

        return db.update(UPDATE_TOUR, params);
    }

    @Override
    public boolean deleteTour(String id) throws SQLException {
        ArrayList<Object> params = new ArrayList<>();
        params.add(id);

        return db.delete(DELETE_TOUR, params);
    }
}
