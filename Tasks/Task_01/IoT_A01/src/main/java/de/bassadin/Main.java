package de.bassadin;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;

public class Main {
    public static void main(String[] args) throws Exception {
        ClientAlice clientAlice = new ClientAlice();
        ClientBob clientBob = new ClientBob();

        clientAlice.setOtherClientReference(clientBob);
        clientBob.setOtherClientReference(clientAlice);

        clientBob.subscribeToOtherClientKeyExchangeTopic();
        clientAlice.subscribeToOtherClientKeyExchangeTopic();
        clientAlice.publishInitialDiffieHellmanKeyExchangeMessage();
    }
}
