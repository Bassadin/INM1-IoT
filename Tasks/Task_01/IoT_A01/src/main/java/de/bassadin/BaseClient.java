package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttException;

public abstract class BaseClient {
    protected Long privateKey;
    protected Long diffieHellmanKey;
    protected HellmanMQTTClient hellmanMQTTClient;

    public BaseClient(Long privateKey, String clientName) throws MqttException {
        this.privateKey = privateKey;
        this.diffieHellmanKey = this.calculateDiffieHellmanKey();
        this.hellmanMQTTClient = new HellmanMQTTClient(clientName);
    }

    protected Long calculateDiffieHellmanKey() {
        return Helpers.calculateDiffieHellmanKey(Helpers.publicKey1, this.privateKey, Helpers.publicKey2);
    }
}
