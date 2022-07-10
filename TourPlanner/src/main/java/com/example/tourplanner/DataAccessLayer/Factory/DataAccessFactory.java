package com.example.tourplanner.DataAccessLayer.Factory;

import com.example.tourplanner.BuisnessLayer.Manager.ConfigPropManager;
import com.example.tourplanner.DataAccessLayer.Database.DatabaseActivity;
import com.example.tourplanner.DataAccessLayer.Database.TourDAO;
import com.example.tourplanner.DataAccessLayer.Database.TourLogDAO;
import com.example.tourplanner.DataAccessLayer.Interface.DAO.ITourDAO;
import com.example.tourplanner.DataAccessLayer.Interface.DAO.ITourLogDAO;
import com.example.tourplanner.DataAccessLayer.Interface.IDatabaseActivity;
import com.example.tourplanner.DataAccessLayer.Interface.IFileActivity;
import com.example.tourplanner.DataAccessLayer.LocalFiles.LocalFilesActivity;

public class DataAccessFactory {
    private static IDatabaseActivity db;

    public static ITourDAO getTourDao(){
        try {
            Class<ITourDAO> cls = (Class<ITourDAO>) Class.forName(TourDAO.class.getName());
            return cls.getConstructor().newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ITourLogDAO getTourLogDao(){
        try {
            Class<ITourLogDAO> cls = (Class<ITourLogDAO>) Class.forName(TourLogDAO.class.getName());
            return cls.getConstructor().newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static IFileActivity getFileActivity(){
        try {
            Class<IFileActivity> cls = (Class<IFileActivity>) Class.forName(LocalFilesActivity.class.getName());
            return cls.getConstructor().newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static IDatabaseActivity getDb() {
        if (db==null){
            try {
                String cs = ConfigPropManager.getConfigProp("db-cs");
                Class<DatabaseActivity> cls = (Class<DatabaseActivity>) Class.forName(DatabaseActivity.class.getName());
                db = cls.getConstructor(String.class).newInstance(cs);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return db;
    }
}
