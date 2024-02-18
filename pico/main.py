import gpscalc,leddisplay
from mpu6050 import init_mpu6050, get
import machine
from machine import Pin,I2C,Timer
from pygps import MicropyGPS
import time
from jsonhandler import jsonhandler
import gpscalc
from temp import temp
#variyable declaration
maxspeed=0
distance=0
longitude=0.0
latitude=0.0
config=""
lastknownlongitude=0.0
lastknownlatitude=0.0
speed=0

time.sleep(4)
#object declaration
i2c = I2C(1, sda=Pin(2), scl=Pin(3), freq=400000)
gps=machine.UART(0,baudrate=9600,bits=8)
gsm=machine.UART(0,baudrate=9600,bits=8)

pygps = MicropyGPS(location_formatting="dd")
jsonhandler=jsonhandler()
#jsonhandler.set_start_point(12.939845850536333, 77.6956380120795)
#jsonhandler.start_point_reset()
#temp=temp("Null","sakthisaran","qmkd14IbbDomtT4H9-e7GE-lKp6-vEh6")
#blynk=temp.getblink()
oled_width = 128
oled_height = 64
oled = leddisplay.SSD1306_I2C(oled_width, oled_height, i2c)
init_mpu6050(i2c)

def getdata(self):
    global maxspeed,distance,longitude,latitude,config,lastknownlongitude,lastknownlatitude,speed
    gpsdata=gpscalc.get_gps_data(gps,pygps)
    config=jsonhandler.read_data()
    latitude=(gpsdata["lat"])
    longitude=(gpsdata["long"])
    speed=(gpsdata["speed"])
    lastknownlatitude=(gpscalc.lastknownlat)
    lastknownlongitude=(gpscalc.lastknownlong)
    #distance=0
    
    if(config["gps"]["startpoint"]["rangelock"]):
        originlat=config["gps"]["startpoint"]["lat"]
        originlong=config["gps"]["startpoint"]["long"]
        speed=config["gps"]["startpoint"]["maxspeed"]
        if(speed>0):
            if(speed>maxspeed):
                maxspeed=speed
                blynk.virtual_write(6,str(maxspeed))
            pass
        if(not(latitude==0.0 or longitude ==0.0)):
            #print(latitude==0.0)
            distance=gpscalc.haversine(float(originlat),float(originlong),float(latitude),float(longitude))
        elif(not(lastknownlatitude==0.0 or lastknownlongitude==0.0)):
            distance=gpscalc.haversine(float(originlat),float(originlong),float(lastknownlatitude),float(lastknownlongitude))
        print(distance)
    #print(config)
    
   # temp.run()
    
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
    
def sendtoserver(self):
    global latitude,longitude
    print("server")
    if(not(latitude==0.0 or longitude ==0.0)):
        print(str(latitude)+" , "+str(longitude))
    

timer=Timer(-1)
timer.init(period=100,mode=Timer.PERIODIC,callback=getdata)
#timer1=Timer(-2)
#timer1.init(period=1000,mode=Timer.PERIODIC,callback=sendtoserver)



    


