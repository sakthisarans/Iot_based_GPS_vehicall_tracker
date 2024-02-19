package com.example.gpstracker.pojo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Devices {
    @NotBlank
    private String vehicalno;
    @NotBlank
    private String deviceid;
    private String name;
    private RangeLimiter rangeLimiters;

    public Devices(String id,String vehicalno,String name){
        this.deviceid=id;
        this.rangeLimiters=new RangeLimiter();
        this.vehicalno=vehicalno;
        if(name!=""){
            this.name=name;
        }else{
            this.name=vehicalno;
        }
    }
}
