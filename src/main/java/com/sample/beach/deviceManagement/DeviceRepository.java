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
        this.devices = new HashMap<>();
    }

    public List<Device> getDevices() {
        return new ArrayList<>(devices.values());
    }

    public Device getDeviceForId(int id) {
        return this.devices.get(id);
    }

    public Device addDevice(String deviceName, String email, String type) {
        Device device = new Device(idCounter++, deviceName, email, type);
        devices.put(device.id, device);
        return device;
    }

    public Device updateDevice(Integer id, String deviceName, String email, String type) {

        Device device = devices.get(id);
        System.out.println("name: "+ device.name);
        if (device != null) {
            if (deviceName != null) device.name = deviceName;
            if (email != null) device.email = email;
            if (type != null) device.type = type;
        }
        System.out.println("name after: "+ device.name);
        return device;
    }

    public boolean removeDevice(Integer id) {
        return false;
    }
}
