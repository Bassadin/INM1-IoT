package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.util.Objects;

public class ClientBob extends BaseClient {

    public ClientBob() throws Exception {
        super(69L, "ClientBob");

//        this.hellmanMQTTClient.disconnectClient();
    }
}