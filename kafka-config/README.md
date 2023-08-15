# Kafka Configuration

This folder contains the configuration files for your Apache Kafka setup. You can modify these files to configure your Kafka broker according to your requirements.

## Prerequisites

- [Apache Kafka](https://kafka.apache.org/) installed and extracted.
- Basic understanding of Kafka configuration parameters.

## Files

### `server.properties`

This file contains the configuration parameters for your Kafka broker. You can modify settings related to server basics, log retention policy, Zookeeper connection, and more.

Please note that this is a sample configuration file. You should customize it based on your deployment environment and use case.

## Steps

1. Make a copy of the `server.properties` file in this folder.

2. Open the copied `server.properties` file in a text editor.

3. Modify the configuration parameters as needed. Be careful while modifying parameters related to listeners, advertised.listeners, log directories, retention policies, and Zookeeper connection.

4. Save the changes to the file.

5. Use this configured `server.properties` file when starting your Kafka broker.
