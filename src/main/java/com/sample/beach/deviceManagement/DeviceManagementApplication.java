package com.sample.beach.deviceManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class DeviceManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(DeviceManagementApplication.class, args);
		context.getEnvironment().setActiveProfiles("default");
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... strings) throws Exception {

//		List<Device> devices = jdbcTemplate.query("select id from devices", new ResultSetExtractor<List<Device>>() {
//			@Nullable
//			@Override
//			public List<Device> extractData(ResultSet rs) throws SQLException, DataAccessException {
//				List<Device> s = new ArrayList<>();
//				while (rs.next()) {
//					Device d = new Device();
//					d.setId(rs.getInt(1));
//					s.add(d);
//				}
//				return s;
//			}
//		});
//		System.out.print(devices);
	}
}
