package com.example.gpstracker.repo;

import com.example.gpstracker.pojo.DeviceMapper;
import com.example.gpstracker.pojo.GpsCordinates;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GpsRepo extends MongoRepository<GpsCordinates,String> {
    @Query("{'deviceid':?0}")
    public GpsCordinates fingByDeviceId(String id);
}
