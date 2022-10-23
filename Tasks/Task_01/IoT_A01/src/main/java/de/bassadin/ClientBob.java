package de.bassadin;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;

public class ClientBob extends BaseClient {

    public ClientBob() throws Exception {
        super(69L, "ClientBob");

        this.hellmanMQTTClient.publishMqttMessage("Hello from Bob!", "hellman/greetings");
        this.hellmanMQTTClient.disconnectClient();
    }
}