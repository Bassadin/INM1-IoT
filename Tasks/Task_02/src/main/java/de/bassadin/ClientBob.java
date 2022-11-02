package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.util.Objects;

public class ClientBob extends BaseClient {

    public static String machineDataExchangeConfirmationTopic = BaseClient.machineDataExchangeTopic + "/confirmation";

    long g;
    long p;
    long b;
    long A;
    long B;
    long K;

    public ClientBob() throws Exception {
        super("ClientBob");


    }

    public void subscribeToOtherClientKeyExchangeTopic() throws MqttException {
        this.hellmanMQTTClient.mqttClient.subscribe(otherClientReference.getKeyExchangeTopicString(), (String topic, MqttMessage mqttMessage) -> {
            this.printLogWithClientIdPrefix("received subscribed message: " + mqttMessage.toString());
            for (String s : mqttMessage.toString().split(",")) {
                String[] varsParts = s.split("=");
                String varName = varsParts[0];
                long varValue = Long.parseLong(varsParts[1]);

                switch (varName) {
                    case "g" -> this.g = varValue;
                    case "p" -> this.p = varValue;
                    case "A" -> this.A = varValue;
                }

            }
            this.printLogWithClientIdPrefix("g: " + g);
            this.printLogWithClientIdPrefix("p: " + p);
            this.printLogWithClientIdPrefix("A: " + A);


            this.b = Helpers.randomIntBetween(1L, this.p);

            this.B = Helpers.calculateDiffieHellmanFormula(g, b, p);
            this.K = Helpers.calculateDiffieHellmanFormula(A, b, p);

            this.printLogWithClientIdPrefix("B: " + B);
            this.printLogWithClientIdPrefix("K: " + K);

            this.hellmanMQTTClient.publishMqttMessage("B=" + B, this.getKeyExchangeTopicString());
        });
    }


    public void subscribeToReceiveMachinePieceDataTopic() throws MqttException {
        this.hellmanMQTTClient.mqttClient.subscribe(ClientAlice.machineDataExchangeRequestTopic, (String topic, MqttMessage mqttMessage) -> {
            String decryptedMessage = Helpers.decryptMessageWithKey(K, mqttMessage.toString());

            String[] messageParts = decryptedMessage.split("=");
            double workpieceSize = Double.parseDouble(messageParts[1]);
            boolean isWorkpieceSizeInBounds = workpieceSize >= 29.95 && workpieceSize <= 30.05;

            String answerMessage = (isWorkpieceSizeInBounds ? "OK" : "NOK") + " " + messageParts[0];
            String encryptedMessage = Helpers.encryptMessageWithKey(K, answerMessage);

            this.hellmanMQTTClient.publishMqttMessage(encryptedMessage, ClientBob.machineDataExchangeConfirmationTopic);
        });
    }
}