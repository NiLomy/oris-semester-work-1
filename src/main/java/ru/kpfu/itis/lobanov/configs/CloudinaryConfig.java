package ru.kpfu.itis.lobanov.configs;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.lobanov.util.CloudinaryUtil;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return CloudinaryUtil.getInstance();
    }
}
