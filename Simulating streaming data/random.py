from time import sleep
from kafka import KafkaProducer
import random
import json

# Kafka configuration
bootstrap_servers = ['localhost:9092']
topic = 'healthcare_data'

# Create a Kafka producer
producer = KafkaProducer(bootstrap_servers=bootstrap_servers,
                         value_serializer=lambda v: json.dumps(v).encode('utf-8'))

# Simulate streaming data
while True:
    patient_id = random.randint(1, 100)
    heart_rate = random.randint(60, 120)
    temperature = round(random.uniform(36.0, 38.5), 2)
    data = {'patient_id': patient_id, 'heart_rate': heart_rate, 'temperature': temperature}
    
    # Send data to Kafka topic
    producer.send(topic, value=data)
    
    print(f"Sent: {data}")
    sleep(1)  # Simulate 1-second interval
