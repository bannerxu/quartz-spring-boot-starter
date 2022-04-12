package io.github.bannerxu.quartz.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {


    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        return new DriverManagerDataSource(properties.getUrl(), properties.getUsername(), properties.getPassword());
    }
}
