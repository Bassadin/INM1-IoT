package de.bassadin;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;

public class Main {
    public static void main(String[] args) throws Exception {
        // https://github.com/eclipse/paho.mqtt.java
        String topic = "MQTT Examples";
        String content = "Message from MqttPublishSample";
        int qos = 2;
        String brokerHostName = "tcp://221419df-1da2-441d-85a2-451d3809358b.fr.bw-cloud-instance.org";
        String clientId = "Java MQTT Client 1";

        MqttClient mqttClient = new MqttClient(brokerHostName, clientId);

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);

        System.out.println("Connecting to broker: " + brokerHostName);
        mqttClient.connect(mqttConnectOptions);
        System.out.println("Connected");

        System.out.println("Publishing message: " + content);
        MqttMessage mqttMessage = new MqttMessage(content.getBytes());
        mqttMessage.setQos(qos);

        mqttClient.publish(topic, mqttMessage);
        System.out.println("Message published");

        mqttClient.disconnect();
        System.out.println("Disconnected");

        System.exit(0);

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