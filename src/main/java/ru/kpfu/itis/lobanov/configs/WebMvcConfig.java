package ru.kpfu.itis.lobanov.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/test").setViewName("test");
        registry.addViewController(ServerResources.ABOUT_US_URL).setViewName(ServerResources.ABOUT_US_PAGE);
        registry.addViewController(ServerResources.PRIVACY_POLICY_URL).setViewName(ServerResources.PRIVACY_POLICY_PAGE);
        registry.addViewController(ServerResources.TERMS_AND_CONDITIONS_URL).setViewName(ServerResources.TERMS_AND_CONDITIONS_PAGE);
    }
}
