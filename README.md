# Healthcare Analytics Project (Healthcare Analytics)

This project, named "Healthcare Analytics," aims to revolutionize healthcare analytics by introducing real-time data streaming, event-based execution, and complex event processing (CEP). Leveraging technologies such as Apache Flink, Apache Kafka, and Python Digital Twin, this project provides real-time insights into healthcare data, enabling timely interventions and proactive patient care.

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
