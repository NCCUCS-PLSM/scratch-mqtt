package nccu.cs.plsm;

/**
 * Created by veck on 15/4/13.
 */

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttListener implements MqttCallback
{
    @Override
    public void connectionLost(Throwable arg0)
    {
        System.out.println("connection lost: " + arg0.toString());
        // do nothing
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0)
    {
        System.out.println("delivery complete: " + arg0.toString());
        // do nothing
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception
    {
        System.out.println("arrived message: " + mqttMessage.toString());
    }
}
