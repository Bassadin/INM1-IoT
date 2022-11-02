package de.bassadin.Clients;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ClientBob extends BaseClient {

    public static String machineDataExchangeConfirmationTopic = BaseClient.machineDataExchangeTopic + "/confirmation";

    public ClientBob() throws Exception {
        super("ClientBob");
    }

    public void subscribeToReceiveMachinePieceDataTopic() throws MqttException {
        this.hellmanMQTTClient.mqttClient.subscribe(ClientAlice.machineDataExchangeRequestTopic, (String topic, MqttMessage mqttMessage) -> {
            String[] messageParts = mqttMessage.toString().split("=");
            double workpieceSize = Double.parseDouble(messageParts[1]);
            boolean isWorkpieceSizeInBounds = workpieceSize >= 29.95 && workpieceSize <= 30.05;

            String answerMessage = (isWorkpieceSizeInBounds ? "OK" : "NOK") + " " + messageParts[0];

            this.hellmanMQTTClient.publishMqttMessage(answerMessage, ClientBob.machineDataExchangeConfirmationTopic);
        });
    }
}