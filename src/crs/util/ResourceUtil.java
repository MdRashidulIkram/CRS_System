package crs.util;

import java.io.File;

public class ResourceUtil {
    public static String get(String fileName) {
        try {
            return new File(
                ResourceUtil.class.getClassLoader().getResource(fileName).toURI()
            ).getAbsolutePath();
        } catch (Exception e) {
            System.err.println("Resource not found: " + fileName);
            return null;
        }
    }
}