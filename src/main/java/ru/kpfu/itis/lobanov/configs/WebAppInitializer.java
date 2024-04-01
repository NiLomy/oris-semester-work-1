package ru.kpfu.itis.lobanov.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

import javax.servlet.Filter;

@Configuration
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[]{ServerResources.HOME_URL};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                CoreConfig.class,
                PersistenceConfig.class,
                WebAppConfig.class,
                CloudinaryConfig.class,
                SecurityConfig.class,
                WebMvcConfig.class,
                AuthConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebAppConfig.class};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding(ServerResources.ENCODING);
        encodingFilter.setForceEncoding(true);
        return new Filter[]{encodingFilter};
    }
}
