package com.sample.beach.deviceManagement;


public class Device {

    String id;

    public Device(String id) {
        super();
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((Device)obj).id);
    }
}
