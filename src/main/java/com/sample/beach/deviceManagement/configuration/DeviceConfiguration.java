package com.sample.beach.deviceManagement.configuration;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class DeviceConfiguration {

    @Bean(name = "deviceDataSource")
    @Profile("local")
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setServerName("localhost");
        dataSource.setPortNumber(3306);
        dataSource.setDatabaseName("deviceManagement");
        dataSource.setUser("testUser");
        dataSource.setPassword("password");
        return dataSource;
    }

    @Bean(name = "deviceDataSource")
    @Profile(
            value={"cloud", "h2"}
    )
    public DataSource defaultDataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .ignoreFailedDrops(true)
                .build();
    }
}
