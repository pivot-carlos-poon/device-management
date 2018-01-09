package com.sample.beach.deviceManagement.repository;

import com.sample.beach.deviceManagement.entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@Qualifier("jpaRepo")
public class DeviceJpaRepository implements DeviceRepoInterface {

    @Autowired
    DataSource dataSource;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Device> getDevices() {
        TypedQuery<Device> namedQuery = entityManager.createNamedQuery("find_all_devices", Device.class);
        return namedQuery.getResultList();
    }

    public Device getDeviceForId(int id) {
        return entityManager.find(Device.class, id);
    }

    public Device addDevice(String deviceName, String email, String type) {
        return entityManager.merge(new Device(deviceName, email, type));
    }

    public Device updateDevice(Integer id, String deviceName, String email, String type) {
        Device device = getDeviceForId(id);
        if (deviceName != null) device.setName(deviceName);
        if (email!= null) device.setEmail(email);
        if (type!= null) device.setType(type);
        return entityManager.merge(device);

    }

    public boolean removeDevice(Integer id) {
        Device device = getDeviceForId(id);
        if (device == null) {
            return false;
        }
        entityManager.remove(device);
        return true;
    }
}