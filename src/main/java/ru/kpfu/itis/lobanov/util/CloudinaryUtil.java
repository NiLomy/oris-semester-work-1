package ru.kpfu.itis.lobanov.util;

import com.cloudinary.Cloudinary;
import ru.kpfu.itis.lobanov.util.configurations.CloudinaryConfigProvider;
import ru.kpfu.itis.lobanov.util.configurations.ConfigProvider;
import ru.kpfu.itis.lobanov.util.constants.LogMessages;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;
import ru.kpfu.itis.lobanov.util.exception.CloudinaryException;
import ru.kpfu.itis.lobanov.util.exception.ConfigException;

import java.util.HashMap;
import java.util.Map;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            ConfigProvider configProvider = new CloudinaryConfigProvider();
            Map<String, String> configMap = new HashMap<>();
            try {
                configMap.put(ServerResources.CLOUDINARY_NAME_KEY, configProvider.readData(ServerResources.CLOUDINARY_NAME_KEY));
                configMap.put(ServerResources.CLOUDINARY_API_KEY, configProvider.readData(ServerResources.CLOUDINARY_API_KEY));
                configMap.put(ServerResources.CLOUDINARY_API_SECRET_KEY, configProvider.readData(ServerResources.CLOUDINARY_API_SECRET_KEY));
                cloudinary = new Cloudinary(configMap);
            } catch (ConfigException e) {
                throw new CloudinaryException(LogMessages.GET_CONFIGURATION_CLOUDINARY_EXCEPTION, e);
            }
        }
        return cloudinary;
    }

    private CloudinaryUtil() {
    }
}
