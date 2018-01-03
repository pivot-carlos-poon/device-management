package com.sample.beach.deviceManagement;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeviceController {

    @Autowired
    DeviceRepository deviceRepository;

    public List<Device> fetchDevices() {
        return Arrays.asList(
                new Device("123")
        );
    }
}
