import org.eclipse.ditto.client.DittoClient;
import org.eclipse.ditto.client.DittoClients;
import org.eclipse.ditto.client.configuration.DittoClientConfiguration;
import org.eclipse.ditto.client.configuration.DittoNamespaceConfiguration;
import org.eclipse.ditto.client.messaging.dittoProtocol.DittoFeature;
import org.eclipse.ditto.client.twin.Twin;
import org.eclipse.ditto.client.twin.TwinKey;
import org.eclipse.ditto.client.twin.commands.Feature;
import org.eclipse.ditto.client.twin.commands.PutFeaturesCmd;
import org.eclipse.ditto.model.base.json.JsonSchemaVersion;
import org.eclipse.ditto.model.things.FeatureData;
import org.eclipse.ditto.model.things.Properties;

public class PatientDigitalTwin {

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

        // Create a Digital Twin for a patient
        Twin twin = dittoClient.twin("your-namespace", "your-thing-id");

        // Define the properties of the patient's Digital Twin
        Properties properties = Properties.newBuilder()
                .put("firstName", "John")
                .put("lastName", "Doe")
                .put("age", 30)
                .build();

        // Create a feature for the heart rate
        FeatureData heartRateFeature = FeatureData.newBuilder()
                .setSchema(JsonSchemaVersion.V_1_0)
                .put("heartRate", 70)
                .build();

        // Create the Digital Twin with properties and features
        PutFeaturesCmd putFeaturesCmd = PutFeaturesCmd.newBuilder()
                .put(new TwinKey(Feature.HEART_RATE.getName()), heartRateFeature)
                .build();

        twin.putFeatures(putFeaturesCmd, properties);

        // Update the heart rate feature
        FeatureData updatedHeartRateFeature = FeatureData.newBuilder()
                .setSchema(JsonSchemaVersion.V_1_0)
                .put("heartRate", 75)
                .build();

        twin.putFeature(new TwinKey(Feature.HEART_RATE.getName()), updatedHeartRateFeature);
    }
}
