from kafka import KafkaProducer
import json
import time
import random
from digital_twin.heart_rate_twin import HeartRateMonitorDigitalTwin

# Kafka configuration
bootstrap_servers = ['localhost:9092']
topic = 'heart_rate_data'

producer = KafkaProducer(bootstrap_servers=bootstrap_servers,
                         value_serializer=lambda v: json.dumps(v).encode('utf-8'))

def simulate_heart_rate(twin):
    while True:
        new_rate = 70 + (random.random() * 40)  # Simulate a heart rate between 70 and 110
        twin.update_heart_rate(new_rate)
        producer.send(topic, value={'patient_id': twin.patient_id, 'heart_rate': twin.heart_rate})
        time.sleep(60)  # Simulate data every minute

if __name__ == "__main__":
    patient_id = 123  # Replace with an actual patient ID
    heart_rate_twin = HeartRateMonitorDigitalTwin(patient_id)
    simulate_heart_rate(heart_rate_twin)
