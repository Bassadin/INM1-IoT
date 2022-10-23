package de.bassadin;

public class Helpers {
    private Long calculateDiffieHellmanKey(Long publicKey1, Long privateKey, Long publicKey2) {
        if (privateKey == 1) {
            return publicKey1;
        } else {
            return ((long) Math.pow(publicKey1, privateKey)) % publicKey2;
        }
    }
}
