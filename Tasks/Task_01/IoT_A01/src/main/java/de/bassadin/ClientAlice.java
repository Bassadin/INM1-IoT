package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Objects;

public class ClientAlice extends BaseClient {

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
        this.hellmanMQTTClient.mqttClient.subscribe(otherClientReference.getKeyExchangeTopicString(), this::handleKeyExchangeMessage);
    }

    protected void handleKeyExchangeMessage(String topic, MqttMessage mqttMessage) throws MqttException {
        String messageString = mqttMessage.toString();
        this.printLogWithClientIdPrefix("received subscribed message: " + messageString);

        String[] varsParts = messageString.split("=");
        long dhBValue = Long.parseLong(varsParts[1]);

        this.K = Helpers.calculateDiffieHellmanFormula(dhBValue, a, p);

        this.printLogWithClientIdPrefix("K: " + K);
    }
}