package de.bassadin.Clients;

import com.google.gson.Gson;
import de.bassadin.Confirmation;
import de.bassadin.Helpers;
import de.bassadin.Workpiece;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ClientAlice extends BaseClient {
    public static String machineDataExchangeRequestTopic = BaseClient.machineDataExchangeTopic + "/request";

    public ClientAlice() throws Exception {
        super("ClientAlice");
    }

    public void subscribeToReceiveMachinePieceConfirmationTopic() throws MqttException {
        this.hellmanMQTTClient.mqttClient.subscribe(
                ClientBob.machineDataExchangeConfirmationTopic,
                (String topic, MqttMessage mqttMessage) -> {
                    Gson gson = new Gson();
                    Confirmation confirmation = gson.fromJson(mqttMessage.toString(), Confirmation.class);

                    printLogWithClientIdPrefix("Received confirmation from bob: " + confirmation.toString());
                });
    }

    public int incrementingWorkpieceNumber = 0;

    public void sendRandomMachinePieceDataForConfirmation() throws MqttException {
        double workPieceSize = Helpers.randomFloatBetween(29.85, 30.15);

        Gson gson = new Gson();
        Workpiece workpiece = new Workpiece(workPieceSize, incrementingWorkpieceNumber);

        this.hellmanMQTTClient.publishMqttMessage(gson.toJson(workpiece), ClientAlice.machineDataExchangeRequestTopic);
        incrementingWorkpieceNumber++;
    }
}