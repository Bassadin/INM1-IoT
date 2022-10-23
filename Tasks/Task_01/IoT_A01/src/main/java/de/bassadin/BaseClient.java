package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttException;

public abstract class BaseClient {
    protected Long privateKey;
    protected Long diffieHellmanKey;
    protected HellmanMQTTClient hellmanMQTTClient;
    protected String clientName;

    public BaseClient(Long privateKey, String clientName) throws MqttException {
        this.privateKey = privateKey;
        this.clientName = clientName;

        this.diffieHellmanKey = this.calculateDiffieHellmanKey();
        System.out.println("Calculated Hellman key for client " + this.clientName + ": " + this.diffieHellmanKey);
        this.hellmanMQTTClient = new HellmanMQTTClient(this.clientName);
    }

    protected Long calculateDiffieHellmanKey() {
        return Helpers.calculateDiffieHellmanKey(Helpers.publicKey1, this.privateKey, Helpers.publicKey2);
    }
}
