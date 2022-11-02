package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Objects;

public class ClientAlice extends BaseClient {
    public static String machineDataExchangeRequestTopic = BaseClient.machineDataExchangeTopic + "/request";

    public ClientAlice() throws Exception {
        super("ClientAlice");
    }

    public void subscribeToReceiveMachinePieceConfirmationTopic() throws MqttException {
        this.hellmanMQTTClient.mqttClient.subscribe(
                ClientBob.machineDataExchangeConfirmationTopic,
                (String topic, MqttMessage mqttMessage) -> {
                    printLogWithClientIdPrefix("Received confirmation from bob: " + mqttMessage.toString());
                });
    }

    public int incrementingWorkpieceNumber = 0;

    public void sendRandomMachinePieceDataForConfirmation() throws MqttException {

        double workPieceSize = Helpers.randomFloatBetween(29.85, 30.15);
        String zeroPaddedWorkpieceNumber = String.format("%02d", incrementingWorkpieceNumber);

        this.hellmanMQTTClient.publishMqttMessage(zeroPaddedWorkpieceNumber + "=" + workPieceSize, ClientAlice.machineDataExchangeRequestTopic);
        incrementingWorkpieceNumber++;
    }
}