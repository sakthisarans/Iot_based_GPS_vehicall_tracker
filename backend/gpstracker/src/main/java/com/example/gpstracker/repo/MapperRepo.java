package com.example.gpstracker.repo;

import com.example.gpstracker.pojo.User;
import com.example.gpstracker.pojo.DeviceMapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MapperRepo extends MongoRepository<DeviceMapper,String> {
    @Query("{'deviceid':?0}")
    public DeviceMapper fingByDeviceId(String id);
}
