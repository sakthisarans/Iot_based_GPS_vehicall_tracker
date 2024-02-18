package com.example.gpstracker.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter@Setter
public class RangeLimiter {
    private boolean rangelimiterstat;
    private String latitude;
    private String longitude;
    private String range;
    public RangeLimiter(){
        this.rangelimiterstat=false;
    }
}
