package me.lofienjoyer.quiet.baseservice.tests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Sets up integration tests
 */
@Configuration
public class IntegrationTestsProfile {

    /**
     * Sets up a temporary data source for tests
     * @return Temporary data source for tests
     */
    @Bean
    @Profile("test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("user");
        dataSource.setPassword("password");

        return dataSource;
    }

}
