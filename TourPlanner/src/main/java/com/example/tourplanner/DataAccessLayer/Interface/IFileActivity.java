package com.example.tourplanner.DataAccessLayer.Interface;

import java.io.File;

public interface IFileActivity {
    String createFile(String name, byte[] arr);
    boolean deleteFile(String name);
    File readFile(String name);
}
