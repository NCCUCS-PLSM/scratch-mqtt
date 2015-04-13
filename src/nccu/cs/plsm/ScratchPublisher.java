package nccu.cs.plsm;

/**
 * Created by veck on 15/4/13.
 */

import java.io.*;
import java.net.*;
import java.util.*;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ScratchPublisher {
    private static final int PORT = 12345; // set to your extension's port number
    private static int volume = 8; // replace with your extension's data, if any

    private static InputStream sockIn;
    private static OutputStream sockOut;

    public static void main(String[] args) throws IOException{

        if(args.length<1)
        {
            System.err.println("Usage: java ScratchPublisher [port number(12345)]");
            return;
        }

        ServerSocket serverSock = new ServerSocket(PORT);
        while(true)
        {
            Socket sock = serverSock.accept();
            sockIn = sock.getInputStream();
            sockOut = sock.getOutputStream();
            try {
                handleRequest();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.out.println("unknown server error");
            }
            sock.close();
        }
    }

    // Handle request from Scratch
    private static void handleRequest() throws IOException {
        String httpBuf = "";
        int i;

        // read data until the first HTTP header line is complete (i.e. a '\n' is seen)
        while ((i = httpBuf.indexOf('\n')) < 0) {
            byte[] buf = new byte[5000];
            int bytes_read = sockIn.read(buf, 0, buf.length);
            if (bytes_read < 0) {
                System.out.println("Socket closed; no HTTP header.");
                return;
            }
            httpBuf += new String(Arrays.copyOf(buf, bytes_read));
        }

        String header = httpBuf.substring(0, i);
        if (header.indexOf("GET ") != 0) {
            System.out.println("This server only handles HTTP GET requests.");
            return;
        }
        i = header.indexOf("HTTP/1");
        if (i < 0) {
            System.out.println("Bad HTTP GET header.");
            return;
        }
        header = header.substring(5, i - 1);
        if (header.equals("favicon.ico")) return; // igore browser favicon.ico requests
        else doCommand(header);
    }

    /* Parse command sent from Scratch */
    private static void doCommand(String cmdAndArgs) {
        // Essential: handle commands understood by this server
        String response = "okay";
        String[] parts = cmdAndArgs.split("/");
        String cmd = parts[0];
        //System.out.print(cmdAndArgs);
        if (cmd.equals("raiseHand")) {
            System.out.println("Raise hand");
            publishToBroker("raiseHand");
        } else if (cmd.equals("putdownHand")) {
            System.out.println("Put hand down");
            publishToBroker("putdownHand");
        } else if (cmd.equals("poll")) {
            // do nothing
        } else {
            System.out.println("unknown command: " + cmd);
        }
    }

    private static void publishToBroker(String cmd){
        String clientId = "pub";
        MemoryPersistence persistence = new MemoryPersistence();
        try
        {
            // connect
            MqttClient sampleClient = new MqttClient("tcp://localhost:1883", clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);

            // prepare a message
            String content = cmd;
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage();
            message.setPayload(content.getBytes());
            System.out.println(message.toString());
            message.setQos(0);// set QoS level to 0

            // publish a message to a Topic: "testScratch"
            sampleClient.publish("testScratch", message);
            System.out.println("Message published");

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





