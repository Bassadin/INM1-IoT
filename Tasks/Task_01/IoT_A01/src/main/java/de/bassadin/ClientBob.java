package de.bassadin;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;

public class ClientBob {
    static Long privateKeyBob = 69L;

    public static void main(String[] args) throws Exception {

        HellmanMQTTClient mqttClientBob = new HellmanMQTTClient("ClientBob");

        mqttClientBob.publishMqttMessage("Hello from Bob!", "hellman/greetings");
        mqttClientBob.disconnectClient();


        String data = "Satz, den man verschlüsseln kann, muss man aber nicht!";

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

    }
}