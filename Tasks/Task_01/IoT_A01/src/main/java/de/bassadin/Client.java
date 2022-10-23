package de.bassadin;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;

public class Client {
    static Long publicKey1 = 3461049572396L;
    static Long publicKey2 = 98751942343464357L;

    static Long user1PrivateKey = 42L;
    static Long user2PrivateKey = 69L;

    public static void main(String[] args) throws Exception {

        HellmanMQTTClient demoClient = new HellmanMQTTClient();

        demoClient.publishMqttMessage("Message from MqttPublishSample");
        demoClient.disconnectClient();


        String data = "Satz, den man verschlüsseln kann, muss man aber nicht!";
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(256);
            Key secretKey = keygen.generateKey();

            // Encryption instance
            Cipher aesEncryptCipherInstance = Cipher.getInstance("AES");
            aesEncryptCipherInstance.init(Cipher.ENCRYPT_MODE, secretKey);

            // Decryption instance
            Cipher aesDecryptCipherInstance = Cipher.getInstance("AES");
            aesDecryptCipherInstance.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] dataAsBytes = data.getBytes("UTF8");

            // Encrypt
            byte[] encryptedBytes = aesEncryptCipherInstance.doFinal(dataAsBytes);
            String encryptedString = new String(encryptedBytes);

            // Decrypt
            byte[] decryptedBytes = aesDecryptCipherInstance.doFinal(encryptedBytes);
            String decryptedString = new String(decryptedBytes);

            System.out.println("Original String : (" + data + ")");
            System.out.println("Encrypt String : (" + encryptedString + ")");
            System.out.println("Decrypt String : (" + decryptedString + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}