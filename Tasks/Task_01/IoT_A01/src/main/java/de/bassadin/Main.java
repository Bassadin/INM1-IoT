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
        String clientId = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(brokerHostName, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + brokerHostName);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }

        String data = "Satz, den man verschlüsseln kann, muss man aber nicht!";
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(256);//,random);

            Key secretKey = keygen.generateKey();
            Cipher aesCipherInstance = Cipher.getInstance("AES");

            byte[] dataAsBytes = data.getBytes("UTF8");
            aesCipherInstance.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = aesCipherInstance.doFinal(dataAsBytes);
            String encryptedString = new String(encryptedBytes);
            aesCipherInstance = Cipher.getInstance("AES");
            aesCipherInstance.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = aesCipherInstance.doFinal(encryptedBytes);
            String decryptedString = new String(decryptedBytes);

            System.out.println("Original String : (" + data + ")");
            System.out.println("Encrypt String : (" + encryptedString + ")");
            System.out.println("Decrypt String : (" + decryptedString + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}