package com.example.gpstracker.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter@Setter@NoArgsConstructor
public class FallDetection {
    private String latitude;
    private String longitude;
    private Date falldate;
}
