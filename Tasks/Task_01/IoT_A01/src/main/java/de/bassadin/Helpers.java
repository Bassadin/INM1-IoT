package de.bassadin;

public class Helpers {
    static final Long publicKey1 = 3461049572396L;
    static final Long publicKey2 = 98751942343464357L;

    public static Long calculateDiffieHellmanKey(Long publicKey1, Long privateKey, Long publicKey2) {
        if (privateKey == 1) {
            return publicKey1;
        } else {
            return ((long) Math.pow(publicKey1, privateKey)) % publicKey2;
        }
    }
}
