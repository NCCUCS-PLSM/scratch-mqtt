package nccu.cs.plsm;

/**
 * Created by veck on 15/4/13.
 */

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Subscriber {

    public static void main(String[] args)
    {
        String clientId = "sub";
        MemoryPersistence persistence = new MemoryPersistence();
        try
        {
            // connect
            MqttAsyncClient sampleClient = new MqttAsyncClient("tcp://localhost:1883", clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.setCallback(new MqttListener());
            IMqttToken conToken = sampleClient.connect(connOpts);
            conToken.waitForCompletion();

            // subscribe
            String topicName = "testScratch";
            System.out.println("Subscribing to topic \"" + topicName + "\" QoS " + 0);
            IMqttToken subToken = sampleClient.subscribe(topicName, 0, null, null);
            subToken.waitForCompletion();
            System.out.println("Subscribed to topic \"" + topicName + "\"");

            // continue waiting for messages until the Enter is pressed
            System.out.println("Press <Enter> to exit");
            try
            {
                System.in.read();
            }
            catch (Exception e)
            {
            // If we can't read we'll just exit
            }

            // disconnect
            sampleClient.disconnect();
            System.out.println("Disconnected");

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
