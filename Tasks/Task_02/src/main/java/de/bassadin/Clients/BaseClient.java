package de.bassadin.Clients;

import de.bassadin.Helpers;
import org.eclipse.paho.client.mqttv3.MqttException;

public abstract class BaseClient {
    // Topics
    public static String greetingsTopic = "hellman/greetings";
    public static String keyExchangeBaseTopic = "hellman/key-exchange/";

    public static String machineDataExchangeTopic = "hellman/machine-data";
    protected String keyExchangeTopicString;
    protected HellmanMQTTClient hellmanMQTTClient;
    protected String clientName;

    protected BaseClient otherClientReference;

    public BaseClient(String clientName) throws MqttException {
        this.clientName = clientName;

        this.hellmanMQTTClient = new HellmanMQTTClient(this.clientName);

        this.keyExchangeTopicString = keyExchangeBaseTopic + this.clientName;

        this.hellmanMQTTClient.publishMqttMessage("Hello from " + this.clientName, greetingsTopic);
    }

    protected void printLogWithClientIdPrefix(String message) {
        Helpers.printLogWithPrefix(this.clientName, message);
    }

    public String getKeyExchangeTopicString() {
        return keyExchangeTopicString;
    }

    public void setOtherClientReference(BaseClient otherClientReference) {
        this.otherClientReference = otherClientReference;
    }
}
