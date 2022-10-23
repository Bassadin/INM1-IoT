package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Objects;

public abstract class BaseClient {
    // Topics
    public static String greetingsTopic = "hellman/greetings";
    public static String keyExchangeBaseTopic = "hellman/keyexchange/";
    protected String keyExchangeTopicString;
    protected Long privateKey;
    protected Long diffieHellmanKey;
    protected HellmanMQTTClient hellmanMQTTClient;
    protected String clientName;

    protected BaseClient otherClientReference;
    protected Boolean isDiffieHellmanKeyValidated = false;

    public BaseClient(Long privateKey, String clientName) throws MqttException {
        this.privateKey = privateKey;
        this.clientName = clientName;

        this.diffieHellmanKey = this.calculateDiffieHellmanKey();
        System.out.println("Calculated Hellman key for client " + this.clientName + ": " + this.diffieHellmanKey);
        this.hellmanMQTTClient = new HellmanMQTTClient(this.clientName);

        this.keyExchangeTopicString = keyExchangeBaseTopic + this.clientName;

        this.hellmanMQTTClient.publishMqttMessage("Hello from " + this.clientName, greetingsTopic);
    }

    protected Long calculateDiffieHellmanKey() {
        return Helpers.calculateDiffieHellmanKey(Helpers.publicKey1, this.privateKey, Helpers.publicKey2);
    }

    protected void printLogWithClientIdPrefix(String message) {
        Helpers.printLogWithPrefix(this.clientName, message);
    }

    protected void handleKeyExchangeMessage(String topic, MqttMessage mqttMessage) {
        this.printLogWithClientIdPrefix("received subscribed message: " + mqttMessage.toString());
        if (Objects.equals(mqttMessage.toString(), String.valueOf(this.diffieHellmanKey))) {
            this.printLogWithClientIdPrefix("Keys match!");
            isDiffieHellmanKeyValidated = true;
        }
    }

    protected void sendDiffieHellmanKey() throws MqttException {
        this.hellmanMQTTClient.publishMqttMessage(String.valueOf(this.diffieHellmanKey), this.keyExchangeTopicString);
    }

    public String getKeyExchangeTopicString() {
        return keyExchangeTopicString;
    }

    public void setOtherClientReference(BaseClient otherClientReference) throws MqttException {
        this.otherClientReference = otherClientReference;

        this.hellmanMQTTClient.mqttClient.subscribe(otherClientReference.getKeyExchangeTopicString(), this::handleKeyExchangeMessage);
    }
}
