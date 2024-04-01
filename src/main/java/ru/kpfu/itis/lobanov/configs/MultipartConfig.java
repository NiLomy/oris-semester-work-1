package ru.kpfu.itis.lobanov.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

@Configuration
public class MultipartConfig {
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(ServerResources.MAX_REQUEST_SIZE);
        multipart.setMaxInMemorySize(ServerResources.MAX_FILE_SIZE);
        return multipart;
    }

    @Bean
    @Order(0)
    public MultipartFilter multipartFilter() {
        MultipartFilter multipartFilter = new MultipartFilter();
        multipartFilter.setMultipartResolverBeanName(ServerResources.MULTIPART_RESOLVER_BEAN);
        return multipartFilter;
    }
}
