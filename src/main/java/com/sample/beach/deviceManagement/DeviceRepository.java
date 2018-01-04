package com.sample.beach.deviceManagement;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class DeviceRepository {

    private Integer idCounter = 0;
    private HashMap<Integer, Device> devices;

    public DeviceRepository() {
        this.devices = new HashMap<Integer, Device>();
    }

    public List<Device> getDevices() {
        return new ArrayList<Device>(devices.values());
    }

    public Device addDevice(String deviceName, String email, String type) {
        Device device = new Device(idCounter++, deviceName, email, type);
        devices.put(device.id, device);
        return device;
    }

    public Device updateDevice(Integer id, String deviceName, String email, String type) {
        //1) see if device with id exists
        //2) update
    }
}
