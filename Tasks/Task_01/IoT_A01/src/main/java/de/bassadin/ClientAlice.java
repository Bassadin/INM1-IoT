package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Objects;

public class ClientAlice extends BaseClient {


    public static String machineDataExchangeRequestTopic = BaseClient.machineDataExchangeTopic + "/request";

    long a;
    long g;
    long p = 99971L;
    long A;
    long K;

    public ClientAlice() throws Exception {
        super("ClientAlice");

        this.a = Helpers.randomIntBetween(1L, this.p);
        this.g = Helpers.randomIntBetween(1L, this.p);
        this.A = Helpers.calculateDiffieHellmanFormula(g, a, p);

        this.printLogWithClientIdPrefix("a: " + a);
        this.printLogWithClientIdPrefix("g: " + g);
        this.printLogWithClientIdPrefix("p: " + p);
        this.printLogWithClientIdPrefix("A: " + A);
    }

    public void publishInitialDiffieHellmanKeyExchangeMessage() throws MqttException {
        String messageContent = "g=" + this.g + ",p=" + this.p + ",A=" + A;
        this.hellmanMQTTClient.publishMqttMessage(messageContent, this.getKeyExchangeTopicString());
    }

    public void subscribeToOtherClientKeyExchangeTopic() throws MqttException {
        this.hellmanMQTTClient.mqttClient.subscribe(otherClientReference.getKeyExchangeTopicString(), (String topic, MqttMessage mqttMessage) -> {
            String messageString = mqttMessage.toString();
            this.printLogWithClientIdPrefix("received subscribed message: " + messageString);

            String[] varsParts = messageString.split("=");
            long dhBValue = Long.parseLong(varsParts[1]);

            this.K = Helpers.calculateDiffieHellmanFormula(dhBValue, a, p);

            this.printLogWithClientIdPrefix("K: " + K);
        });
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