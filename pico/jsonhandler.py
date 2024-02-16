import ujson as json

class jsonhandler():
    def __init__(self):
        
        self.data= {"gps":{"startpoint":{"rangelock":False,"lat":"","long":"","maxspeed":""}},"falldetection":{"falldetected":False,"time":"","lat":"","long":""}}
    
    def read_data(self):
        try:
            with open("savedata.json") as fp:
                data = json.loads(fp.read())
            return data
        except Exception as ex:
            print("Error! Could not read"+str(ex))
    def set_start_point(self,lat,long,speed=0):
        self.data["gps"]["startpoint"]["rangelock"]=True
        self.data["gps"]["startpoint"]["lat"]=lat
        self.data["gps"]["startpoint"]["long"]=long
        self.data["gps"]["startpoint"]["maxspeed"]=speed
        print(self.data)
        try:
            with open('savedata.json', 'w') as f:
                json.dump(self.data, f)
        except Exception as ex:
            print("Error! Could not save"+str(ex))
    def start_point_reset(self):
        self.data["gps"]["startpoint"]["rangelock"]=False
        self.data["gps"]["startpoint"]["lat"]=""
        self.data["gps"]["startpoint"]["long"]=""
        self.data["gps"]["startpoint"]["maxspeed"]=""
        #print(self.data)
        try:
            with open('savedata.json', 'w') as f:
                json.dump(self.data, f)
        except Exception as ex:
            print("Error! Could not save"+str(ex))
#jsonhandler()