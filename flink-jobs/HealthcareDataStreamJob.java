import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class AbnormalHeartRatePattern {
    public static void main(String[] args) throws Exception {
        // Set up the Flink execution environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create a Kafka data stream containing healthcare sensor data
        DataStream<String> kafkaDataStream = env
                .addSource(new FlinkKafkaConsumer<>("healthcare_data", new SimpleStringSchema(), properties));

        // Define a pattern to detect abnormal heart rates
        Pattern<JsonNode, ?> abnormalHeartRatePattern = Pattern.<JsonNode>begin("start")
                .where(new SimpleCondition<JsonNode>() {
                    @Override
                    public boolean filter(JsonNode value) throws Exception {
                        int heartRate = value.get("heart_rate").asInt();
                        return heartRate > 100; // Define your abnormal heart rate threshold
                    }
                })
                .within(Time.seconds(5)); // Define the time window for the pattern

        // Create a pattern stream using CEP
        PatternStream<JsonNode> patternStream = CEP.pattern(
                kafkaDataStream.map(new MapFunction<String, JsonNode>() {
                    @Override
                    public JsonNode map(String value) throws Exception {
                        ObjectMapper mapper = new ObjectMapper();
                        return mapper.readTree(value);
                    }
                }),
                abnormalHeartRatePattern);

        // Define a PatternSelectFunction to process detected patterns and generate
        // alerts
        DataStream<String> alerts = patternStream.select(new PatternSelectFunction<JsonNode, String>() {
            @Override
            public String select(Map<String, JsonNode> pattern) throws Exception {
                StringBuilder alertMessage = new StringBuilder("Abnormal heart rate detected for patient: ");
                for (Map.Entry<String, JsonNode> entry : pattern.entrySet()) {
                    alertMessage.append(entry.getValue().get("patient_id").asText());
                    alertMessage.append(", ");
                }
                return alertMessage.toString();
            }
        });

        // Print the alerts to the console (you can replace this with any desired output
        // sink)
        alerts.print();

        // Execute the Flink job
        env.execute("Abnormal Heart Rate Pattern Detection");
    }
}
