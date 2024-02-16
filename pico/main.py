import gpscalc,leddisplay
from mpu6050 import init_mpu6050, get
import machine
from machine import Pin,I2C
from pygps import MicropyGPS
import time
from jsonhandler import jsonhandler
import gpscalc 

time.sleep(4)

i2c = I2C(1, sda=Pin(2), scl=Pin(3), freq=400000)
gps=machine.UART(0,baudrate=9600,bits=8)

pygps = MicropyGPS(location_formatting="dd")
jsonhandler=jsonhandler()
#jsonhandler.set_start_point(12.939845850536333, 77.6956380120795)
#jsonhandler.start_point_reset()
oled_width = 128
oled_height = 64
oled = leddisplay.SSD1306_I2C(oled_width, oled_height, i2c)
init_mpu6050(i2c)



while True:
    gpsdata=gpscalc.get_gps_data(gps,pygps)
    config=jsonhandler.read_data()
    latitude=(gpsdata["lat"])
    longitude=(gpsdata["long"])
    speed=(gpsdata["speed"])
    lastknownlatitude=(gpscalc.lastknownlat)
    lastknownlongitude=(gpscalc.lastknownlong)
    distance=0
    if(config["gps"]["startpoint"]["rangelock"]):
        originlat=config["gps"]["startpoint"]["lat"]
        originlong=config["gps"]["startpoint"]["long"]
        speed=config["gps"]["startpoint"]["maxspeed"]
        if(speed>0):
            pass
        if(not(latitude==0.0 or longitude ==0.0)):
            #print(latitude==0.0)
            distance=gpscalc.haversine(float(originlat),float(originlong),float(latitude),float(longitude))
        elif(not(lastknownlatitude==0.0 or lastknownlongitude==0.0)):
            distance=gpscalc.haversine(float(originlat),float(originlong),float(lastknownlatitude),float(lastknownlongitude))
        print(distance)
    #print(config)
    
    data=get(i2c)
    oled.fill(0)
    oled.show()
    oled.contrast(150) 
    oled.text("temp:"+str(data["temp"]),0,8)
    oled.text("lat	:" +str(latitude),0,16)
    oled.text("long	:" +str(longitude),0,24)
    oled.text("speed:" +str(speed),0,32)
    #oled.text("lkla:" +lastknownlatitude,0,40)
    #oled.text("lklo:" +lastknownlongitude,0,48)
    oled.text("distance :" +str(distance),0,40)
    oled.show()
    time.sleep(0.1)


