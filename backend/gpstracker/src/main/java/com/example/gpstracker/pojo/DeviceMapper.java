package com.example.gpstracker.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter@Setter@NoArgsConstructor
public class DeviceMapper {
    @Id
    private String id;
    private String uname;
    private String deviceid;
    private boolean rmchange;

}
