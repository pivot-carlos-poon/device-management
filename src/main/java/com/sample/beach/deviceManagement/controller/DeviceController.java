package com.sample.beach.deviceManagement.controller;

import com.sample.beach.deviceManagement.requestbody.DeviceRequestBody;
import com.sample.beach.deviceManagement.entity.Device;
import com.sample.beach.deviceManagement.repository.DeviceRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("devices")
public class DeviceController {

    private final DeviceRepoInterface deviceRepoInterface;

    @Autowired
    public DeviceController(@Qualifier("jpaRepo") DeviceRepoInterface deviceRepoInterface) {
        this.deviceRepoInterface = deviceRepoInterface;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Device>> fetchDevices() {
        return ResponseEntity.ok(deviceRepoInterface.getDevices());
    }

    @PostMapping("/add")
    public ResponseEntity<Device> addDevice(String deviceName, String email, String type) {
        Device device = deviceRepoInterface.addDevice(deviceName, email, type);

        if(device != null){
            return ResponseEntity.ok(device);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path="/update/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<Device> updateDevice(@PathVariable("id") Integer id, @RequestBody(required = false) DeviceRequestBody requestBody) {

        Device device = deviceRepoInterface.updateDevice(id, requestBody.getName(), requestBody.getEmail(), requestBody.getType());
        if (device != null) {
            return ResponseEntity.ok(device);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Integer> removeDevice(@PathVariable("id") Integer id) {

        boolean deviceRemoved = deviceRepoInterface.removeDevice(id);

        if (deviceRemoved) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable("id") Integer id) {
        Device device = deviceRepoInterface.getDeviceForId(id);
        if (device != null) {
            return ResponseEntity.ok(device);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }
    }
}
