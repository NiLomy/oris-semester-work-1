package ru.kpfu.itis.lobanov.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.kpfu.itis.lobanov.util.constants.ServerResources;

@Configuration
@ComponentScan(basePackages = {ServerResources.BASE_PACKAGE})
public class CoreConfig {
}
