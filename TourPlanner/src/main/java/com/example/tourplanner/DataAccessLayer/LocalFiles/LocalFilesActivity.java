package com.example.tourplanner.DataAccessLayer.LocalFiles;

import com.example.tourplanner.DataAccessLayer.Interface.IFileActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class LocalFilesActivity implements IFileActivity {

    private String basePath = Paths.get("src",
            "main",
            "resources",
            "images").toFile().getAbsolutePath();

    @Override
    public String createFile(String name, byte[] arr) {
        try {
            File f2s = new File(basePath, name);
            FileOutputStream fos = new FileOutputStream(f2s);
            fos.write(arr);
            fos.close();
            String path = basePath +""+ name;
            return path;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteFile(String name) {
        String path = basePath + "/" +  name + ".jpg";
        File f = new File(path);

        if(f.delete())
            return true;
        return false;
    }

    @Override
    public File readFile(String name) {
        String path = basePath + "/" +  name + ".jpg";
        File f = new File(path);

        return f;
    }
}
