#!/usr/bin/python3

from kafka import KafkaProducer
from random import gauss
from time import sleep
import sys
import json

server = "localhost:9092"

## Heart rate BPM (Beat per minute)
HR_MU = 70
HR_SD = 20

def getHeartRate() :
    return int(gauss(HR_MU, HR_SD))

## Systolic Blood Pressure: mmHg
SBP_MU = 120
SBP_SD = 25

def getSystolicBloodPressure() :
    return int(gauss(SBP_MU, SBP_SD))

## Temperature Celsius
TEMP_MU = 37
TEMP_SD = 0.5

def getTemperature() :
    return format(gauss(TEMP_MU, TEMP_SD), '.2f')

def main():
    ## the topic 
    topic = sys.argv[1]

    ## create a Kafka producer with json serializer
    producer = KafkaProducer(value_serializer=lambda v: json.dumps(v).encode('utf-8'),
                             bootstrap_servers=server)
    print("*** Starting measurements stream on " + server + ", topic : " + topic)

    try:
        while True:
            for userId in range(1, 1000):
                ## Generate random measurements
                meas1 = { "userid": "%d" % userId, "type": "HR", "value": getHeartRate()}
                producer.send(topic, value=meas1, key=str(userId).encode('utf-8'))

                meas2 = { "userid": "%d" % userId, "type": "TEMP", "value": getTemperature()}
                producer.send(topic, value=meas2, key=str(userId).encode('utf-8'))

                meas3 = { "userid": "%d" % userId, "type": "SBP", "value": getSystolicBloodPressure()}
                producer.send(topic, value=meas3, key=str(userId).encode('utf-8'))

                print("Sending HR   : %s" % (json.dumps(meas1).encode('utf-8')))
                print("Sending TEMP : %s" % (json.dumps(meas2).encode('utf-8')))
                print("Sending BP   : %s" % (json.dumps(meas3).encode('utf-8')))

            sleep(1)

    except KeyboardInterrupt:
        pass

    print("\nIntercepted user interruption ..\nBlock until all pending messages are sent..")
    producer.flush()

if __name__ == "__main__":
    main()
