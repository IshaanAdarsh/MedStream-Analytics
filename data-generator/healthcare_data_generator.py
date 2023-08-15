# Sample script to simulate healthcare sensor data
from kafka import KafkaProducer
import random
import json
from time import sleep

# Kafka configuration
bootstrap_servers = ['localhost:9092']
topic = 'healthcare_data'

producer = KafkaProducer(bootstrap_servers=bootstrap_servers,
                         value_serializer=lambda v: json.dumps(v).encode('utf-8'))

# Simulate streaming data
while True:
    patient_id = random.randint(1, 100)
    heart_rate = random.randint(60, 120)
    temperature = round(random.uniform(36.0, 38.5), 2)
    data = {'patient_id': patient_id, 'heart_rate': heart_rate, 'temperature': temperature}
    
    producer.send(topic, value=data)
    print(f"Sent: {data}")
    sleep(1)  # Simulate 1-second interval
