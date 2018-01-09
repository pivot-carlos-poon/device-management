package com.sample.beach.deviceManagement;

import com.sample.beach.deviceManagement.configuration.DeviceConfiguration;
import com.sample.beach.deviceManagement.entity.Device;
import com.sample.beach.deviceManagement.repository.DeviceRepoInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {DeviceManagementApplication.class, DeviceConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class DeviceRepositoryTest {

    @Autowired
    @Qualifier("h2Repo")
//    @Qualifier("jpaRepo")
    DeviceRepoInterface deviceRepository;

    @Before
    public void setUp() throws Exception {
        deviceRepository.addDevice("fakeDevice1", "fakeEmail1", "fakeType1");
        deviceRepository.addDevice("fakeDevice2", "fakeEmail2", "fakeType2");
    }

    @Test
    public void testShouldShowDisplayListOfDevices() {

        List<Device> devices = deviceRepository.getDevices();
        assertEquals(2, devices.size());
        Device firstDevice = devices.get(0);
        assertEquals("fakeDevice1", firstDevice.getName());
        assertEquals("fakeEmail1", firstDevice.getEmail());
        assertEquals("fakeType1", firstDevice.getType());

        Device lastDevice = devices.get(1);
        assertEquals("fakeDevice2", lastDevice.getName());
        assertEquals("fakeEmail2", lastDevice.getEmail());
        assertEquals("fakeType2", lastDevice.getType());
    }

    @Test
    public void testShouldSearchForSpecificDeviceByID() {
        Device firstDevice = deviceRepository.getDeviceForId(1);
        assertEquals("fakeDevice1", firstDevice.getName());
        assertEquals("fakeEmail1", firstDevice.getEmail());
        assertEquals("fakeType1", firstDevice.getType());
    }

    @Test
    public void testShouldAddDeviceToDB() {
        assertEquals(2, deviceRepository.getDevices().size());

        Device addedDevice = deviceRepository.addDevice("Phone 5", "new@email.com", "Paperweight");
        assertNotNull(addedDevice);
        assertEquals("Phone 5", addedDevice.getName());
        assertEquals("new@email.com", addedDevice.getEmail());
        assertEquals("Paperweight", addedDevice.getType());

        List storedDevices = deviceRepository.getDevices();
        assertEquals(3, storedDevices.size());
    }

    @Test
    public void testShouldRemoveDevice() {
        assertNotNull(deviceRepository.getDeviceForId(1));
        deviceRepository.removeDevice(1);
        assertNull(deviceRepository.getDeviceForId(1));
    }

}
