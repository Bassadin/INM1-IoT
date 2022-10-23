package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Objects;

public class ClientAlice extends BaseClient {


    public ClientAlice() throws Exception {
        super(42L, "ClientAlice");
    }
}