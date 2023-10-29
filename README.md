# MedStream Analytics (Healthcare Analytics) 

MedStream Analytics aims to revolutionize healthcare analytics by introducing real-time data streaming, event-based execution, and complex event processing (CEP). Leveraging technologies such as Apache Flink, Apache Kafka, and Python Digital Twin, this project provides real-time insights into healthcare data, enabling timely interventions and proactive patient care.

This project is a participant in the GE Healthcare Precision Care Challenge 2023. Out of numerous entries, we were selected as one of the 12 finalists out of the 447 Teams participating. Our project has consistently demonstrated its innovative potential and earned its place among the top finalists in this prestigious competition.

## Directory Structure

- `README.md`: This documentation file.
- `producer/`
  - `icu.py`: Python script for simulating healthcare data generation.
- `kafka-config/`
  - `server.properties`: Kafka server configuration file.
  - `README.md`: Kafka configuration documentation.
- `icu-alarm/`
  - `target/`: Contains compiled Java code and JAR files.
  - `src/`: Java source code for the Flink application.
  - `pom.xml`: Maven project configuration file.
  - `flink-connector-kafka-0.9_2.10-1.3.0.jar`: Flink Kafka connector JAR.
- `digital-twin/`
  - `Digital-twin-juyp/`: Jupyter notebook for generating a digital twin of a vital signs monitor.
    - `output.png`: Output image from the Jupyter notebook.
    - `GE.ipynb`: Jupyter notebook for the digital twin simulation.
  - `digital-twin-models-data/`: JSON data for digital twin models.
    - `patient-digital-twin.json`: Patient digital twin model.
    - `medical-device-digital-twin.json`: Medical device digital twin model.

## Digital Twin Simulation

The `GE.ipynb` Jupyter notebook generates a digital twin of a vital signs monitor, which measures the patient's heart rate, temperature, and blood pressure. It is designed to alert doctors and staff by sending SMS and email notifications when a failure signature or stroke risk is triggered.

https://github.com/IshaanAdarsh/healthcare-analytics/assets/100434702/8cd8c06d-7553-4e34-ae07-9f26b12d91b1

# icu-alarm (Steps to Run Application):
To run your Flink application and the associated Kafka setup, you can follow these step-by-step instructions. This guide assumes that you have already built your Flink application and Kafka topic producer code.

**Step 1: Start ZooKeeper**

```shell
./bin/zkServer.sh start
```

**Step 2: Start Kafka Server**

```shell
./bin/kafka-server-start.sh config/server.properties
```

**Step 3: Delete Existing Kafka Topic (Optional)**

This step is optional if you want to remove any existing data for the "geCEP" topic.

```shell
./bin/kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic geCEP
```

**Step 4: Create Kafka Topic**

Create the "geCEP" Kafka topic with one partition and one replication factor:

```shell
./bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic geCEP --partitions 1 --replication-factor 1
```

**Step 5: Start Kafka Topic Producer**

Run your Python Kafka topic producer script. Replace `/Users/spartacus/icu-alarm/producer/icu.py` with the correct path to your producer script, if needed. This step starts generating data for your Kafka topic.

```shell
python3 /Users/spartacus/icu-alarm/producer/icu.py geCEP
```


https://github.com/IshaanAdarsh/healthcare-analytics/assets/100434702/0a48dca8-cdd0-4d5f-b40e-ff303304a24a


**Step 6: Start Flink Cluster**

Start the Flink cluster:

```shell
./bin/start-cluster.sh
```

**Step 7: Run Flink Application**

Run your Flink application with the following command:

```shell
./bin/flink run /Users/spartacus/icu-alarm/target/flink-kafka-stroke-risk-1.0-SNAPSHOT-jar-with-dependencies.jar > out.txt
```



https://github.com/IshaanAdarsh/healthcare-analytics/assets/100434702/761594d1-9418-420e-b251-9baebb4b5bd8



This command will execute your Flink job, and the output will be redirected to the `out.txt` file.

**Step 8: Monitor Flink Output**

To monitor the Flink job's output in real-time, you can use the `tail` command:

```shell
tail -f out.txt
```
 

https://github.com/IshaanAdarsh/healthcare-analytics/assets/100434702/ac7359cc-c1a0-40f4-9967-98229ac284fa




This will display the Flink job's output as it is produced.

**To Shut Down the System:**

- For Kafka and the producer, you can stop them by pressing `Ctrl+C` in their respective terminal windows.

- To cancel the Flink job, go to the terminal where you ran the Flink job (`./bin/flink run ...`) and press `Ctrl+C`.

- Stop the ZooKeeper server:

```shell
./bin/zkServer.sh stop
```

- Stop the Flink cluster:

```shell
./bin/stop-cluster.sh
```

This sequence of steps will allow you to run your Flink application alongside Kafka and manage the entire system effectively. Make sure your Flink job logic and Kafka producer code are correctly configured to produce and process data as expected.

## Hackathon Tasks

This project is part of a hackathon, and the following tasks have been completed:
- [x] Create a digital twin of a medical device/component.
- [x] Simulate sensor/log data flowing in from the device.
- [x] Create streaming analytics on the digital twin that generates alerts when a failure signature is detected.
- [x] The failure signature may span across large time span (could be within minutes to multiple days).
- [x] Prove out compute optimization when fleet is scaled to 1000s of devices.

Note: The task "Prove out the change of state performed as part of the fix procedure in the digital twin is applied to the real device" has not been completed.

## Technologies Used

- Apache Kafka (kafka_2.13-3.5.0)
- Apache Flink (flink-1.16.2)
- Apache ZooKeeper (apache-zookeeper-3.8.2-bin)
- Apache Maven (apache-maven-3.9.4)
- IntelliJ IDEA (IDE)

For more details on how to set up and run the project, please refer to the documentation in each directory.
