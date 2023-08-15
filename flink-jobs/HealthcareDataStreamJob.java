import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.cep.CEP;
import org.apache.flink.cep.PatternSelectFunction;
import org.apache.flink.cep.PatternStream;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;

public class HealthcareDataStreamJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> kafkaStream = ... // Create Kafka data stream

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

        // Define a pattern to detect abnormal heart rate
        Pattern<Tuple3<Integer, Integer, Double>, ?> abnormalHeartRatePattern = Pattern.<Tuple3<Integer, Integer, Double>>begin("start")
                .where(new SimpleCondition<Tuple3<Integer, Integer, Double>>() {
                    @Override
                    public boolean filter(Tuple3<Integer, Integer, Double> value) throws Exception {
                        return value.f1 > 100; // Heart rate > 100
                    }
                })
                .within(Time.seconds(5));

        PatternStream<Tuple3<Integer, Integer, Double>> patternStream = CEP.pattern(processedStream.keyBy(value -> value.f0), abnormalHeartRatePattern);

        DataStream<String> abnormalHeartRateAlerts = patternStream.select(new PatternSelectFunction<Tuple3<Integer, Integer, Double>, String>() {
            @Override
            public String select(Map<String, List<Tuple3<Integer, Integer, Double>>> pattern) throws Exception {
                Tuple3<Integer, Integer, Double> lastEvent = pattern.get("start").get(0);
                return "Abnormal heart rate detected for patient " + lastEvent.f0 + ": " + lastEvent.f1;
            }
        });

        abnormalHeartRateAlerts.print();

        env.execute("HealthcareDataStreamJob");
    }
}
