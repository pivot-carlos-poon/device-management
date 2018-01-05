package com.sample.beach.deviceManagement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {DeviceManagementApplication.class, DeviceConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DeviceRepositoryTest {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testShouldShowDisplayListOfDevices() {
        List<Device> devices = deviceRepository.getDevices();
        assertEquals(4, devices.size());
        Device firstDevice = devices.get(0);
        assertEquals("paul smith", firstDevice.getName());
        assertEquals("paul@paul.com", firstDevice.getEmail());
        assertEquals("aPhone", firstDevice.getType());

        Device lastDevice = devices.get(3);
        assertEquals("smith smith", lastDevice.getName());
        assertEquals("smith@smith.com", lastDevice.getEmail());
        assertEquals("00000000 Berry", lastDevice.getType());
    }

    @Test
    public void testShouldAddDeviceToDB() {
        Device addedDevice = deviceRepository.addDevice("Phone 5", "new@email.com", "Paperweight");
        assertNotNull(addedDevice);
        assertEquals("Phone 5", addedDevice.getName());
        assertEquals("new@email.com", addedDevice.getEmail());
        assertEquals("Paperweight", addedDevice.getType());

        List storedDevices = jdbcTemplate.query("select * from devices where name='Phone 5' AND email='new@email.com' AND type='Paperweight'", new BeanPropertyRowMapper<>(Device.class));
        assertEquals(1, storedDevices.size());
    }

    @Test
    public void testShouldRemoveDevice() {
        deviceRepository.removeDevice(1);
        List <Device>storedDevices = jdbcTemplate.query("select * from devices", new BeanPropertyRowMapper<>(Device.class));
        List resultList = storedDevices.stream().filter(device -> device.getId() == 1).collect(Collectors.toList());
        assertEquals(0, resultList.size());
    }

}
