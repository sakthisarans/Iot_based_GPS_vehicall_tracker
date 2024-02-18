package com.example.gpstracker.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter@Setter@NoArgsConstructor
public class GpsCordinates {
    @Id
    private String id;
    private String deviceid;
    private List<GpsLatLong> latnong;
    private List<TopSpeed> maxspeed;
    public List<FallDetection> fallDetection;
}
