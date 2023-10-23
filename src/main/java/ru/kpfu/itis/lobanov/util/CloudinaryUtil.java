package ru.kpfu.itis.lobanov.util;

import com.cloudinary.Cloudinary;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            Map<String, String> configMap = new HashMap<>();
            configMap.put("cloud_name", "dr96a1nqv");
            configMap.put("api_key", "338436573918617");
            configMap.put("api_secret", "NkOsdq7C3Sl_pvyHbzn0-Ilz6M4");
            cloudinary = new Cloudinary(configMap);
        }
        return cloudinary;
    }

    private CloudinaryUtil(){}
}
