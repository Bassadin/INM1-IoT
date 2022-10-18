package de.bassadin;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;

public class Main {
    public static void main(String[] args) throws Exception {
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