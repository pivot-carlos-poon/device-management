package com.sample.beach.deviceManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("devices")
public class DeviceController {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceController(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<Device>> fetchDevices() {
        return ResponseEntity.ok(deviceRepository.getDevices());
    }

    @PostMapping("/add")
    public ResponseEntity<Device> addDevice(String deviceName, String email, String type) {
        Device device = deviceRepository.addDevice(deviceName, email, type);

        if(device != null){
            return ResponseEntity.ok(device);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(path="/update/{id}", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
    public ResponseEntity<Device> updateDevice(@PathVariable("id") Integer id, @RequestBody(required = false) DeviceRequestBody requestBody) {

        Device device = deviceRepository.updateDevice(id, requestBody.getName(), requestBody.getEmail(), requestBody.getType());
        if (device != null) {
            return ResponseEntity.ok(device);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Integer> removeDevice(@PathVariable("id") Integer id) {

        boolean deviceRemoved = deviceRepository.removeDevice(id);

        if (deviceRemoved) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
