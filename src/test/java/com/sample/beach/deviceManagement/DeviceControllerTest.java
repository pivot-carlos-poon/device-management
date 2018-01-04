package com.sample.beach.deviceManagement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeviceControllerTest {

    @InjectMocks
    DeviceController deviceController;

    @Mock
    DeviceRepository deviceRepository;

    @Test
    public void testShouldReturnDeviceResponse() {
        List<Device> expectedDeviceList = Arrays.asList(
                new Device(123, "some phone name",
                        "some@example.com", "APhone"));

        when(deviceRepository.getDevices()).thenReturn(expectedDeviceList );
        List<Device> actualDeviceList = deviceController.fetchDevices();

        assertEquals(expectedDeviceList, actualDeviceList);
    }

    @Test
    public void testShouldAddDevice() {
        deviceController.addDevice("Phone 234", "Bruce@someemail.com", "APhone");
        verify(deviceRepository, times(1))
                .addDevice("Phone 234", "Bruce@someemail.com", "APhone");

    }

}
