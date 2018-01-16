package com.sample.beach.deviceManagement.configuration;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class DeviceConfiguration {

    @Autowired
    private DeviceDBConfiguration serviceConfiguration;

    @Bean(name = "deviceDataSource")
    @Profile("local")
    public DataSource localDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setServerName("localhost");
        dataSource.setPortNumber(3306);
        dataSource.setDatabaseName("deviceManagement");
        dataSource.setUser("testUser");
        dataSource.setPassword("password");
        return dataSource;
    }

    @Bean(name = "deviceDataSource")
    @Profile("h2")
    public DataSource defaultDataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .ignoreFailedDrops(true)
                .build();
    }

    @Bean(name = "deviceDataSource")
    @Profile("cloud")
    public DataSource notDataSource() {

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(serviceConfiguration.getHostname());
        dataSource.setPortNumber(serviceConfiguration.getPort());
        dataSource.setDatabaseName(serviceConfiguration.getName());
        dataSource.setUser(serviceConfiguration.getUsername());
        dataSource.setPassword(serviceConfiguration.getPassword());

        return dataSource;
    }
}
