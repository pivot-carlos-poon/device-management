package com.sample.beach.deviceManagement.repository;

import com.sample.beach.deviceManagement.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Qualifier("h2Repo")
public class DeviceRepository implements DeviceRepoInterface {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;



    public List<Device> getDevices() {
        return jdbcTemplate.query("select * from device", new BeanPropertyRowMapper(Device.class));
    }

    public Device getDeviceForId(int id) {
        List<Device> devices = jdbcTemplate.query("select * from device where id=?", new Object[] { id }, new BeanPropertyRowMapper<>(Device.class));
        if (devices.size() == 0) {
            return null;
        }
        return devices.get(0);
    }

    public Device addDevice(String deviceName, String email, String type) {
        Integer result = jdbcTemplate.update("insert into device (name, email, type) values (?, ?, ?)", new Object[] {deviceName, email, type});
        if (result > 0) {
            return new Device(deviceName, email, type);
        }
        return null;
    }

    public Device updateDevice(Integer id, String deviceName, String email, String type) {

        Device device = getDeviceForId(id);
        System.out.println("name: "+ device.getName());
        if (device != null) {
            if (deviceName != null) device.setName(deviceName);
            if (email != null) device.setEmail(email);
            if (type != null) device.setType(type);
        }
        System.out.println("name after: "+ device.getName());
        return device;
    }

    public boolean removeDevice(Integer id) {
        return jdbcTemplate.update("DELETE from device where id=?", new Object[] {id}) > 0;
    }
}
