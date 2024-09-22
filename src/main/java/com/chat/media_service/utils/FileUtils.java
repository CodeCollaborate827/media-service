package com.chat.media_service.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    public static File createFileFromBytes(byte[] bytes, String fileName) {
        File file = new File(fileName);

        // set the bytes to the file
        try {
            Files.write(file.toPath(), bytes);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf(".");
        if (i == -1) return ""; // if the file has no extension, return empty
        return fileName.substring(i);
    }


}
