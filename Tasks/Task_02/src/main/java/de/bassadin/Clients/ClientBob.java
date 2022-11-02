package de.bassadin.Clients;

import com.google.gson.Gson;
import de.bassadin.Confirmation;
import de.bassadin.Workpiece;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ClientBob extends BaseClient {

    public static String machineDataExchangeConfirmationTopic = BaseClient.machineDataExchangeTopic + "/confirmation";

    public ClientBob() throws Exception {
        super("ClientBob");
    }

    public void subscribeToReceiveMachinePieceDataTopic() throws MqttException {
        this.hellmanMQTTClient.mqttClient.subscribe(ClientAlice.machineDataExchangeRequestTopic, (String topic, MqttMessage mqttMessage) -> {


            Gson gson = new Gson();
            Workpiece workpiece = gson.fromJson(mqttMessage.toString(), Workpiece.class);

            boolean isWorkpieceSizeInBounds = workpiece.getWorkpieceSize() >= 29.95 && workpiece.getWorkpieceSize() <= 30.05;

            Confirmation confirmation = new Confirmation(workpiece.getWorkpieceSize() - 30.00, workpiece.getWorkpieceNumber(), isWorkpieceSizeInBounds);
            String confirmationJsonString = gson.toJson(confirmation);

            this.hellmanMQTTClient.publishMqttMessage(confirmationJsonString, ClientBob.machineDataExchangeConfirmationTopic);
        });
    }
}