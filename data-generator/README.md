# Healthcare Data Generator:

This guide provides step-by-step instructions for setting up the healthcare data generator to simulate streaming healthcare sensor data using Python and Apache Kafka. This simulated data will be used to test and showcase your healthcare analytics project.

## Prerequisites

- Python installed on your system.
- [Apache Kafka](https://kafka.apache.org/) installed and running.
- Python `kafka-python` package installed. You can install it using `pip install kafka-python`.

## Steps

### 1. Setup the Kafka Topic

1. Start your Apache Kafka broker by navigating to the Kafka directory and running:
   ```shell
   bin/zookeeper-server-start.sh config/zookeeper.properties
   bin/kafka-server-start.sh config/server.properties
   ```

2. Create a Kafka topic named 'healthcare_data':
   ```shell
   bin/kafka-topics.sh --create --topic healthcare_data --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
   ```

### 2. Setting Up the Data Generator

1. Create a new directory for the data generator:
   ```shell
   mkdir data-generator
   cd data-generator
   ```

2. Create a Python script, for example, `healthcare_data_generator.py`, using your preferred text editor or IDE.

3. Copy and paste the [sample Python script](data-generator/healthcare_data_generator.py) into the script file.

4. Modify the `bootstrap_servers` variable in the script to match your Kafka broker address. If you're running Kafka locally, you can leave it as is.

### 3. Running the Data Generator

1. Open a terminal and navigate to the directory containing the `healthcare_data_generator.py` script.

2. Run the script using the following command:
   ```shell
   python healthcare_data_generator.py
   ```

3. You should see data being printed in the terminal, indicating that the script is generating simulated sensor readings and sending them to Kafka.

### 4. Verifying Data Generation

1. Open another terminal window.

2. Check the Kafka topic 'healthcare_data' to verify that messages are being produced:
   ```shell
   bin/kafka-console-consumer.sh --topic healthcare_data --bootstrap-server localhost:9092 --from-beginning
   ```

3. You should see the generated data being consumed in the terminal.

### 5. Stopping the Data Generator

1. To stop the data generator, press `Ctrl+C` in the terminal where the script is running.

### 6. Additional Configuration

1. You can customize the data generation logic to match your specific use case, adding more data fields or generating different patterns.

2. For more advanced scenarios, consider implementing realistic data distributions and generating more complex sensor data.
