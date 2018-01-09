package com.sample.beach.deviceManagement;

import com.sample.beach.deviceManagement.controller.DeviceController;
import com.sample.beach.deviceManagement.entity.Device;
import com.sample.beach.deviceManagement.repository.DeviceRepository;
import com.sample.beach.deviceManagement.requestbody.DeviceRequestBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeviceControllerTest {

    @InjectMocks
    DeviceController deviceController;

    @Mock
    DeviceRepository deviceRepository;

    Device testDevice;
    DeviceRequestBody testRequestBody;

    @Before
    public void initializeTests() {
        testDevice = new Device( 123,"some phone name",
                "some@example.com", "APhone");

        testRequestBody = new DeviceRequestBody();

    }

    @Test
    public void testShouldReturnDeviceResponse() {
        List<Device> expectedDeviceList = Arrays.asList(testDevice);

        when(deviceRepository.getDevices()).thenReturn(expectedDeviceList);
        ResponseEntity<List<Device> > response = deviceController.fetchDevices();
        assertEquals(expectedDeviceList, response.getBody());
    }

    @Test
    public void testShouldAddDeviceSucceeds() {
        when(deviceRepository.addDevice("Phone 234", "Bruce@someemail.com", "APhone")).thenReturn(testDevice);
        ResponseEntity<Device> responseEntity = deviceController.addDevice("Phone 234", "Bruce@someemail.com", "APhone");

        assertEquals(responseEntity, ResponseEntity.ok(testDevice));
        verify(deviceRepository, times(1))
                .addDevice("Phone 234", "Bruce@someemail.com", "APhone");
    }

    @Test
    public void testShouldUpdateExistingDeviceAllFields() {
        when(deviceRepository.updateDevice(123, "some phone name",
                "some@example.com", "APhone")).thenReturn(testDevice);
        testRequestBody.setName(testDevice.getName());
        testRequestBody.setEmail(testDevice.getEmail());
        testRequestBody.setType(testDevice.getType());
        ResponseEntity<Device> responseEntity = deviceController.updateDevice(testDevice.getId(), testRequestBody);
        verify(deviceRepository, times(1))
                .updateDevice(testDevice.getId(), testDevice.getName(), testDevice.getEmail(), testDevice.getType());

        assertEquals(responseEntity, ResponseEntity.ok(testDevice));
    }

    @Test
    public void testShouldUpdateExistingDeviceOneField() {
        when(deviceRepository.updateDevice(anyInt(), anyString(), isNull(), isNull())).thenReturn(testDevice);
        testRequestBody.setName(testDevice.getName());
        ResponseEntity<Device> response = deviceController.updateDevice(testDevice.getId(), testRequestBody);
        verify(deviceRepository, times(1))
                .updateDevice(testDevice.getId(), testDevice.getName(), null, null);
        assertEquals(response, ResponseEntity.ok(testDevice));
    }

    @Test
    public void testShouldNotUpdateDeviceThatDoesNotExist() {
        when(deviceRepository.updateDevice(anyInt(), anyString(), anyString(), anyString())).thenReturn(null);
        testRequestBody.setName(testDevice.getName());
        testRequestBody.setEmail(testDevice.getEmail());
        testRequestBody.setType(testDevice.getType());

        ResponseEntity<Device> response = deviceController.updateDevice(123, testRequestBody);
        verify(deviceRepository, times(1)).updateDevice(anyInt(), anyString(), anyString(), anyString());
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
        public void testShouldRemoveExistingDeviceSuccessfully() {
        Integer idToDelete = 123;
        when(deviceRepository.removeDevice(idToDelete)).thenReturn(true);
        ResponseEntity<Integer> responseEntity  = deviceController.removeDevice(idToDelete);
        assertEquals(responseEntity, ResponseEntity.ok(idToDelete));
    }

    @Test
    public void testShouldRemoveNonExistingDeviceSuccessfully() {
        Integer idToDelete = 123;
        when(deviceRepository.removeDevice(idToDelete)).thenReturn(true);
        ResponseEntity responseEntity = deviceController.removeDevice(idToDelete);
        assertEquals(responseEntity, ResponseEntity.ok(idToDelete));
    }

    @Test
    public void testShouldFailToRemoveDevice() {
        when(deviceRepository.removeDevice(anyInt())).thenReturn(false);
        ResponseEntity responseEntity = deviceController.removeDevice(123);
        assertEquals(responseEntity, ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null));
    }
}
