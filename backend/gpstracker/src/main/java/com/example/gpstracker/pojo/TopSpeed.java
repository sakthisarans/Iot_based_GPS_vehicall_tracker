package com.example.gpstracker.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter@Setter@NoArgsConstructor
public class TopSpeed {
    private Date date;
    private String maxspeed;
}
