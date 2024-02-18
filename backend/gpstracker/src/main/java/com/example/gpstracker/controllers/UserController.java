package com.example.gpstracker.controllers;

import com.example.gpstracker.pojo.DeviceMapper;
import com.example.gpstracker.pojo.Devices;
import com.example.gpstracker.pojo.GpsCordinates;
import com.example.gpstracker.pojo.User;
import com.example.gpstracker.repo.GpsRepo;
import com.example.gpstracker.repo.MapperRepo;
import com.example.gpstracker.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepo;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepo = userRepository;
    }
    @Autowired
    MapperRepo mapperRepo;
    @Autowired
    GpsRepo gpsRepo;

    @PostMapping("/register")
    public ResponseEntity<?> registeruser( @RequestBody @Valid User user){
        return new ResponseEntity<>(userRepo.save(user), HttpStatus.OK);
    }
    @GetMapping("/login")
    public ResponseEntity<?> login(){
        return null;
    }
    @GetMapping("/adddevice")
    public ResponseEntity<?> adddevice(@RequestParam(value = "uname",required = true)String uname,
                                       @RequestParam(value = "deviceid",required = true)String deviceid,
                                       @RequestParam(value = "vehicalno",required = true)String vehicalno,
                                       @RequestParam(value = "name",required = false,defaultValue = "")String name){
        User user=userRepo.fingByEmail(uname);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            DeviceMapper dm=mapperRepo.fingByDeviceId(deviceid);
            if(dm==null) {
                Devices devices = new Devices(deviceid, vehicalno, name);
                user.getDevices().add(devices);
                DeviceMapper dev=new DeviceMapper();
                dev.setDeviceid(deviceid);
                dev.setUname(uname);
                dev.setRmchange(false);
                GpsCordinates cord=new GpsCordinates();
                cord.setDeviceid(deviceid);
                gpsRepo.save(cord);
                mapperRepo.save(dev);
                return new ResponseEntity<>(userRepo.save(user), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("device already registered",HttpStatus.NOT_FOUND);
            }
        }
    }
}
