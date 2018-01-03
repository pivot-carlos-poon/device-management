package com.sample.beach.deviceManagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DeviceControllerTest {

    @InjectMocks
    DeviceController deviceController;

    @Mock
    DeviceRepository deviceRepository;

    @Test
    public void testShouldReturnDeviceResponse() {
        List<Device> actualDeviceList = deviceController.fetchDevices();
        List<Device> expectedDeviceList = Arrays.asList(new Device("123"));
        assertEquals(expectedDeviceList, actualDeviceList);

    }

}
