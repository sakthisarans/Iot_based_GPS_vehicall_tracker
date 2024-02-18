package com.example.gpstracker.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter@Setter@NoArgsConstructor
public class GpsLatLong {
    private String latitude;
    private String longitude;
    private Date date;
    public void update(String lat, String lang, Date date){
        this.latitude=lat;
        this.longitude=lang;
        this.date=date;
    }
}
