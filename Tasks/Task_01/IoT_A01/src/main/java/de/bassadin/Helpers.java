package de.bassadin;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class Helpers {

    public static void printLogWithPrefix(String prefix, String message) {
        System.out.println(prefix + " - " + message);
    }

    public static long randomIntBetween(long low, long high) {
        Random r = new Random();
        return r.nextLong(high + 1 - low) + low;
    }

    // https://stackoverflow.com/a/29996205
    public static long calculateDiffieHellmanFormula(long base, long power, long prime) {
        long result = base;

        for (int i = 1; i < power; ++i) {
            result = result * base % prime;

        }
        return result;
    }

    public static double randomFloatBetween(double min, double max) {
        return min + Math.random() * (max - min);
    }

    public static String encryptMessageWithKey(final Long secretLong, final String data) {
        byte[] decodedKey = Base64.getDecoder().decode(secretLong.toString());

        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occured while encrypting data", e);
        }

    }

    public static String decryptMessageWithKey(final Long secretLong, final String encryptedString) {
        byte[] decodedKey = Base64.getDecoder().decode(secretLong.toString());

        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
            return new String(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Error occured while decrypting data", e);
        }
    }
}
