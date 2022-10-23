package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HellmanMQTTClient {
    static final int mqttQOS = 2;
    private String clientId = "Machine Alice";
    static String brokerHostName = "tcp://221419df-1da2-441d-85a2-451d3809358b.fr.bw-cloud-instance.org";

    private MqttClient mqttClient;

    public HellmanMQTTClient() throws MqttException {
        mqttClient = new MqttClient(brokerHostName, clientId);

        System.out.println("Connecting to broker: " + brokerHostName);
        mqttClient.connect();
        System.out.println("Connected");
    }

    public void disconnectClient() throws MqttException {
        this.mqttClient.disconnect();
        System.out.println("Disconnected");
    }

    public HellmanMQTTClient(String clientId) throws MqttException {
        this();
        this.clientId = clientId;
    }

    public void publishMqttMessage(String message, String messageTopic) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(mqttQOS);

        mqttClient.publish(messageTopic, mqttMessage);
        System.out.println("Message published");
    }
}
