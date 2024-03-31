package ru.kpfu.itis.lobanov.configs;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@PropertySource("classpath:persistence.properties")
@EnableJpaRepositories("ru.kpfu.itis.lobanov.model.repositories")
public class PersistenceConfig implements EnvironmentAware {
    private Environment environment;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        String driver = environment.getProperty("jdbc.driver");
        String url = environment.getProperty("jdbc.url");
        String user = environment.getProperty("jdbc.user");
        String password = environment.getProperty("jdbc.password");


        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws PropertyVetoException {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.valueOf(environment.getProperty("database")));
        jpaVendorAdapter.setShowSql(environment.getProperty("jdbc.show-sql", Boolean.class));
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform(environment.getProperty("hibernate.dialect"));

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("ru.kpfu.itis.lobanov.model");
        entityManagerFactory.afterPropertiesSet();

        return entityManagerFactory.getObject();
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceAnnotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager dbTransactionManager() throws PropertyVetoException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory());
        return transactionManager;
    }
}
