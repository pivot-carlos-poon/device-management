package com.sample.beach.deviceManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class DeviceRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    private Integer idCounter = 0;
    private HashMap<Integer, Device> devices;

    public DeviceRepository() {
        this.devices = new HashMap<>();
    }

    public List<Device> getDevices() {
        return jdbcTemplate.query("select * from devices", new BeanPropertyRowMapper(Device.class));
    }

    public Device getDeviceForId(int id) {
        return this.devices.get(id);
    }

    public Device addDevice(String deviceName, String email, String type) {
        Integer result = jdbcTemplate.update("insert into devices (name, email, type) values (?, ?, ?)", new Object[] {deviceName, email, type});
        if (result > 0) {
            return new Device(idCounter++, deviceName, email, type);
        }
        return null;
    }

    public Device updateDevice(Integer id, String deviceName, String email, String type) {

        Device device = devices.get(id);
        System.out.println("name: "+ device.name);
        if (device != null) {
            if (deviceName != null) device.name = deviceName;
            if (email != null) device.email = email;
            if (type != null) device.type = type;
        }
        System.out.println("name after: "+ device.name);
        return device;
    }

    public boolean removeDevice(Integer id) {
        return jdbcTemplate.update("DELETE from devices where id=?", new Object[] {id}) > 0;
    }
}
