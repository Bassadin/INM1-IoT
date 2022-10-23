package de.bassadin;

import org.eclipse.paho.client.mqttv3.*;

public class HellmanMQTTClient {
    static final int mqttQOS = 2;
    static String brokerHostName = "tcp://221419df-1da2-441d-85a2-451d3809358b.fr.bw-cloud-instance.org";

    private String clientId;
    public MqttClient mqttClient;

    public HellmanMQTTClient(String clientId) throws MqttException {
        this.clientId = clientId;

        mqttClient = new MqttClient(brokerHostName, clientId);

        printLogWithClientIdPrefix("Connecting to broker: " + brokerHostName);
        mqttClient.connect();
        printLogWithClientIdPrefix("Connected");
    }

    public void disconnectClient() throws MqttException {
        this.mqttClient.disconnect();
        printLogWithClientIdPrefix("Disconnected");
    }

    public void publishMqttMessage(String messageString, String messageTopic) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(messageString.getBytes());
        mqttMessage.setQos(mqttQOS);

        mqttClient.publish(messageTopic, mqttMessage);
        printLogWithClientIdPrefix("Message published: " + messageString);
    }

    private void printLogWithClientIdPrefix(String message) {
        System.out.println(this.clientId + " - " + message);
    }
}
