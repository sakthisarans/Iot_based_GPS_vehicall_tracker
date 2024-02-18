package com.example.gpstracker.controllers;

import com.example.gpstracker.pojo.*;
import com.example.gpstracker.repo.GpsRepo;
import com.example.gpstracker.repo.MapperRepo;
import com.example.gpstracker.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/tracker")
public class TrackerController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GpsRepo gpsRepo;

    @Autowired
    MapperRepo mapperRepo;
    @GetMapping("/updatecordinate")
    public HttpStatus cordupdate(@RequestParam(value = "lat",required = true)String lat,
                                        @RequestParam(value = "long",required = true)String lang,
                                        @RequestParam(value = "deviceid",required = true)String deviceid){

        GpsCordinates cord=gpsRepo.fingByDeviceId(deviceid);
        if(cord!=null){
            Date date=new Date();

            if(cord.getLatnong()==null)
            {
                System.out.println("if");
                GpsLatLong latlong=new GpsLatLong();
                latlong.update(lat,lang,date);
                List<GpsLatLong> t=new ArrayList<>();
                t.add(latlong);
                GpsCordinates cord1=new GpsCordinates();
                cord1.setId(cord.getId());
                cord1.setDeviceid(cord.getDeviceid());
                cord1.setLatnong(t);
                gpsRepo.save(cord1);
                return HttpStatus.OK;
            }
            else
            {
                System.out.println("else");
                GpsLatLong latlong=new GpsLatLong();
                latlong.update(lat,lang,date);
                cord.getLatnong().add(latlong);
                gpsRepo.save(cord);
                return HttpStatus.OK;
            }
        }else {
            return HttpStatus.FORBIDDEN;
        }
    }

    @GetMapping("/addmaxspeed")
    public HttpStatus addmaxspeed(@RequestParam(value = "deviceid",required = true)String deviceid,
                                  @RequestParam(value = "maxspeed",required = true)String maxspeed){

        GpsCordinates cord=gpsRepo.fingByDeviceId(deviceid);
        if(cord!=null){
            Date date=new Date();

            if(cord.getMaxspeed()==null){
                TopSpeed topSpeed=new TopSpeed();
                topSpeed.setDate(date);
                topSpeed.setMaxspeed(maxspeed);
                List<TopSpeed> ts=new ArrayList<>();
                ts.add(topSpeed);
                cord.setMaxspeed(ts);
                gpsRepo.save(cord);
                return HttpStatus.OK;
            }else{
                TopSpeed topSpeed=new TopSpeed();
                topSpeed.setDate(date);
                topSpeed.setMaxspeed(maxspeed);
                cord.getMaxspeed().add(topSpeed);
                gpsRepo.save(cord);
                return HttpStatus.OK;
            }
        }
        else {
            return HttpStatus.NOT_FOUND;
        }
    }

    @GetMapping("/falldetection")
    public HttpStatus falldetection(@RequestParam(value = "deviceid",required = true)String deviceid,
                                    @RequestParam(value = "lat",required = true)String latitude,
                                    @RequestParam(value = "long",required = true)String longitude){
        GpsCordinates cord=gpsRepo.fingByDeviceId(deviceid);
        if(cord!=null) {
            Date date = new Date();
            if(cord.getFallDetection()==null){
                FallDetection fd=new FallDetection();
                fd.setFalldate(date);
                fd.setLatitude(latitude);
                fd.setLongitude(longitude);
                List<FallDetection> fdarray=new ArrayList<>();
                fdarray.add(fd);
                cord.setFallDetection(fdarray);
                gpsRepo.save(cord);
                return HttpStatus.OK;
            }else{
                FallDetection fd=new FallDetection();
                fd.setFalldate(date);
                fd.setLatitude(latitude);
                fd.setLongitude(longitude);
                cord.getFallDetection().add(fd);
                gpsRepo.save(cord);
                return HttpStatus.OK;
            }
        }else{
            return HttpStatus.FORBIDDEN;
        }
    }

    @GetMapping("/configchange")
    public ResponseEntity<?> confog(@RequestParam(value = "deviceid",required = true)String deviceid){
        List<Devices> dl=new ArrayList<>();
        DeviceMapper dm=mapperRepo.fingByDeviceId(deviceid);
        if(dm!=null){
            if(dm.isRmchange()){
                User user=userRepository.fingByEmail(dm.getUname());
                List<Devices> device=user.getDevices();

                for (Devices i : device){
                    System.out.println(i.getDeviceid());
                    System.out.println(deviceid);
                    if(i.getDeviceid().equals(deviceid)){
                        System.out.println("added");
                        dl.add(i);
                    }
                }
                if(!dl.isEmpty()) {
                    dm.setRmchange(false);
                    mapperRepo.save(dm);
                    return new ResponseEntity<>(dl.get(0).getRangeLimiters(), HttpStatus.OK);
                }else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }else{
                return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

}
