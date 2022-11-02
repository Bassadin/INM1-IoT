package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        ClientAlice clientAlice = new ClientAlice();
        ClientBob clientBob = new ClientBob();

        clientAlice.setOtherClientReference(clientBob);
        clientBob.setOtherClientReference(clientAlice);

        clientAlice.subscribeToReceiveMachinePieceConfirmationTopic();
        clientBob.subscribeToReceiveMachinePieceDataTopic();

        Thread.sleep(500);

        IntStream.rangeClosed(1, 30)
                .forEach((i) -> {
                    try {
                        clientAlice.sendRandomMachinePieceDataForConfirmation();
                        Thread.sleep((long) Helpers.randomFloatBetween(50, 200));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
