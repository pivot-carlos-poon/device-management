package com.sample.beach.deviceManagement.repository;

import com.sample.beach.deviceManagement.entity.Device;

import java.util.List;

public interface DeviceRepoInterface {
    public List<Device> getDevices();

    public Device getDeviceForId(int id);

    public Device addDevice(String deviceName, String email, String type);

    public Device updateDevice(Integer id, String deviceName, String email, String type);

    public boolean removeDevice(Integer id);
}
