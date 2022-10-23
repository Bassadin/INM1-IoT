package de.bassadin;

public class ClientAlice extends BaseClient {
    public ClientAlice() throws Exception {
        super(42L, "ClientAlice");

        this.hellmanMQTTClient.publishMqttMessage("Hello from " + this.clientName, "hellman/greetings");
        this.hellmanMQTTClient.disconnectClient();
    }
}