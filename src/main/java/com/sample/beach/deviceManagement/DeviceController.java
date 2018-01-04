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

    @GetMapping
    @RequestMapping(method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
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

    @PutMapping("/update/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable("id") String id) {
        // String deviceName, String email, String type
        /*
        if(oparamers ! exist){
        retunr
        return BadRequest response
        else
        ...

         */

//        System.out.println("deviceName="+ deviceName + "-- email=" + email + " -- type="+ type);

        return null;
    }
}
