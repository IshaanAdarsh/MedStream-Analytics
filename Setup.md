# icu-alarm

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

This command will execute your Flink job, and the output will be redirected to the `out.txt` file.

**Step 8: Monitor Flink Output**

To monitor the Flink job's output in real-time, you can use the `tail` command:

```shell
tail -f out.txt
```

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
