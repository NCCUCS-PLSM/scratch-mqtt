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
        // do nothing
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0)
    {
        // do nothing
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception
    {
        System.out.println(mqttMessage.toString());
    }
}
