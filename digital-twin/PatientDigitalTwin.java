import org.eclipse.ditto.client.DittoClient;
import org.eclipse.ditto.client.DittoClients;
import org.eclipse.ditto.client.configuration.DittoClientConfiguration;
import org.eclipse.ditto.client.configuration.DittoNamespaceConfiguration;
import org.eclipse.ditto.client.twin.Twin;
import org.eclipse.ditto.client.twin.commands.Feature;
import org.eclipse.ditto.client.twin.commands.PutFeaturesCmd;
import org.eclipse.ditto.model.base.json.JsonSchemaVersion;
import org.eclipse.ditto.model.things.FeatureData;

public class HeartRateDigitalTwin {

        public static void main(String[] args) {
                // Configure the Ditto client
                DittoNamespaceConfiguration namespaceConfig = DittoNamespaceConfiguration.newBuilder()
                                .setEndpoint("http://localhost:8080")
                                .setClientId("your-client-id")
                                .build();

                DittoClientConfiguration clientConfig = DittoClientConfiguration.newBuilder()
                                .setNamespaceConfiguration(namespaceConfig)
                                .build();

                DittoClient dittoClient = DittoClients.newInstance(clientConfig);

                // Create a Digital Twin for heart rate monitoring
                Twin twin = dittoClient.twin("your-namespace", "heart-rate-monitor");

                // Create a feature for the heart rate
                FeatureData heartRateFeature = FeatureData.newBuilder()
                                .setSchema(JsonSchemaVersion.V_1_0)
                                .put("heartRate", 70) // Initialize heart rate to 70
                                .build();

                // Create the Digital Twin with the heart rate feature
                PutFeaturesCmd putFeaturesCmd = PutFeaturesCmd.newBuilder()
                                .put(Feature.HEART_RATE.getName(), heartRateFeature)
                                .build();

                twin.putFeatures(putFeaturesCmd);

                // Update the heart rate feature (simulating a change in heart rate)
                FeatureData updatedHeartRateFeature = FeatureData.newBuilder()
                                .setSchema(JsonSchemaVersion.V_1_0)
                                .put("heartRate", 75) // Simulate an increase in heart rate to 75
                                .build();

                twin.putFeature(Feature.HEART_RATE.getName(), updatedHeartRateFeature);
        }
}
