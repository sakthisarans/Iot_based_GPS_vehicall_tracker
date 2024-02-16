# Import the math module
import math

# Define a function to convert degrees to radians
def to_radians(degrees):
    return degrees * math.pi / 180

# Define a function to calculate the haversine distance
def haversine(lat1, lon1, lat2, lon2):
    # Convert the coordinates to radians
    lat1 = to_radians(lat1)
    lon1 = to_radians(lon1)
    lat2 = to_radians(lat2)
    lon2 = to_radians(lon2)

    # Calculate the differences
    dlat = lat2 - lat1
    dlon = lon2 - lon1

    # Apply the haversine formula
    a = math.sin(dlat / 2) ** 2 + math.cos(lat1) * math.cos(lat2) * math.sin(dlon / 2) ** 2
    c = 2 * math.asin(math.sqrt(a))
    r = 6371 # Radius of the earth in kilometers
    return r * c # Return the distance in kilometers

# Test the function with some example coordinates



#lat1 = 12.939328933341017
#lon1 = 77.6951583164313
#lat2 = 12.938155757114487
#lon2 = 77.6938220809428
#distance = haversine(lat1, lon1, lat2, lon2)
#print("The distance between the two points is", distance, "km")



lastknownlat=0.0
lastknownlong=0.0

def get_gps_data(gps,my_gps):
    global lastknownlat,lastknownlong
    gpsdata=gps.readline()
    try:
        gpsdata=gpsdata.decode()
    except:
        gpsdata=None
    if(gpsdata!=None):
       # print(gpsdata)
        for x in gpsdata:
            my_gps.update(x)
            
        lat=my_gps.latitude
        long=my_gps.longitude
        speed=str(int(float(my_gps.speed_string())))+" km/h"
       # print(speed)
        if(lat[1]=="S"):
            lat=lat[0]*-1
        else:
            lat=lat[0]
        if(long[1]=="W"):
            long=long[0]*-1
        else:
            long=long[0]
            
        #print((lat==0.0 or long ==0.0))
        if(not(lat==0.0 or long ==0.0)):
            lastknownlat=lat
            lastknownlong=long

        return {"lat":lat,"long":long,"speed":speed}
    else:
        return {"lat":"0.00","long":"0.00","speed":"0 km/h"}


