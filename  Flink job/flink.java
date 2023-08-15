import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class HealthcareDataStreamJob {

    public static void main(String[] args) throws Exception {
        // Set up the execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create a stream from Kafka topic (Replace with actual configuration)
        DataStream<String> kafkaStream = env.addSource(new FlinkKafkaConsumer<String>(
            "healthcare_data", new SimpleStringSchema(), kafkaProperties));

        // Transform and process the data
        DataStream<Tuple3<Integer, Integer, Double>> processedStream = kafkaStream.map(new MapFunction<String, Tuple3<Integer, Integer, Double>>() {
            @Override
            public Tuple3<Integer, Integer, Double> map(String value) throws Exception {
                String[] tokens = value.split(",");
                int patientId = Integer.parseInt(tokens[0]);
                int heartRate = Integer.parseInt(tokens[1]);
                double temperature = Double.parseDouble(tokens[2]);
                return new Tuple3<>(patientId, heartRate, temperature);
            }
        });

        // Print the processed data to the console
        processedStream.print();

        // Execute the job
        env.execute("HealthcareDataStreamJob");
    }
}
