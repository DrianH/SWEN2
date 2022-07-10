package com.example.tourplanner.DataAccessLayer.Database;

import com.example.tourplanner.BuisnessLayer.Manager.ConfigPropManager;
import com.example.tourplanner.DataAccessLayer.Interface.IDatabaseActivity;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseActivity implements IDatabaseActivity {
    private final Logger log = LogManager.getLogger(DatabaseActivity.class);
    private String cs;

    public DatabaseActivity(String cs) {
        this.cs = cs;
    }

    private Connection connectToDb() throws SQLException {
        log.info("Connecting to DB");
        String dbUser = ConfigPropManager.getConfigProp("db-user");
        String dbPw = ConfigPropManager.getConfigProp("db-password");

        try {
            return DriverManager.getConnection(cs, dbUser, dbPw);
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            log.error(sqlE);
        }
        throw new SQLException("connecting to DB failed :( (much sad)");
    }

    private List<Map<String, Object>> getDataFromSet(ResultSet set) throws SQLException {
        List<Map<String, Object>> al = new ArrayList<>();
        ResultSetMetaData rsmd = set.getMetaData();
        int cols = rsmd.getColumnCount();
        while (set.next()) {
            HashMap<String, Object> row = new HashMap<>(cols);
            for (int i = 1; i <= cols; ++i) {
                row.put(rsmd.getColumnName(i), set.getObject(i));
            }
            al.add(row);
        }
        return al;
    }

    @Override
    public String add(String query, ArrayList<Object> params) throws SQLException {
        log.info("~~ DatabaseActivity - add");
        log.info("Query\t|" + query);
        log.info("Params\t|" + params);

        try (Connection c = connectToDb();
             PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            // Dynamically add parameters:
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) != null)
                    ps.setString(i + 1, params.get(i).toString());
                else
                    ps.setNull(i + 1, java.sql.Types.NULL);
            }
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next())
                        return generatedKeys.getString(1);
                }
            }
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            log.error(sqlE);
        }
        throw new SQLException("Failed to add data :(");
    }

    @Override
    public boolean update(String query, ArrayList<Object> params) throws SQLException {
        log.info("~~ DatabaseActivity - update");
        log.info("Query\t|" + query);
        log.info("Params\t|" + params);

        try (Connection c = connectToDb();
             PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            for(int i=0; i<params.size(); i++) {
                if(params.get(i) != null)
                    ps.setString(i+1, params.get(i).toString());
                else
                    ps.setNull(i+1, java.sql.Types.NULL);
            }
            int numOfUpdatedRows = ps.executeUpdate();
            if(numOfUpdatedRows > 0)
                return true;
            else
                return false;
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            log.error(sqlE);
        }
        throw new SQLException("Failed to update data :(");
    }

    @Override
    public boolean delete(String query, ArrayList<Object> params) throws SQLException {
        log.info("~~ DatabaseActivity - delete");
        log.info("Query\t|" + query);
        log.info("Params\t|" + params);

        try (Connection c = connectToDb();
             PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            for(int i=0; i<params.size(); i++) {
                if(params.get(i) != null)
                    ps.setString(i+1, params.get(i).toString());
                else
                    ps.setNull(i+1, java.sql.Types.NULL);
            }
            int numOfDeletedRows = ps.executeUpdate();
            if(numOfDeletedRows > 0)
                return true;
            else
                return false;
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            log.error(sqlE);
        }
        throw new SQLException("Failed to delete data :(");
    }

    @Override
    public <T> List<T> read(String query) throws SQLException {
        log.info("~~ DatabaseActivity - read");
        log.info("Query\t|" + query);
        try (Connection c = connectToDb();
             Statement s = c.createStatement();) {
            ResultSet rs = s.executeQuery(query);
            return (List<T>) getDataFromSet(rs);
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            log.error(sqlE);
        }
        throw new SQLException("Failed to read data :(");
    }

    @Override
    public <T> List<T> read(String query, ArrayList<Object> params) throws SQLException {
        log.info("~~ DatabaseActivity - read");
        log.info("Query\t|" + query);
        log.info("Params\t|" + params);

        try (Connection c = connectToDb();
             PreparedStatement ps = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i) != null)
                    ps.setString(i + 1, params.get(i).toString());
                else
                    ps.setNull(i + 1, java.sql.Types.NULL);
            }
            ResultSet rs = ps.executeQuery();
            return (List<T>) getDataFromSet(rs);
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
            log.error(sqlE);
        }
        throw new SQLException("Failed to read data :(");
    }
}
