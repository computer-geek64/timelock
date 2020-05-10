package com.ashishdsouza.TimeLock.Client;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class timelock {
    private static String bytesToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] base64ToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    private static PublicKey base64ToPublicKey(String publicKeyBase64) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] publicKeyBytes = base64ToBytes(publicKeyBase64);
            return keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        }
        catch(NoSuchAlgorithmException | InvalidKeySpecException ex) {
            return null;
        }
    }

    private static PrivateKey base64ToPrivateKey(String privateKeyBase64) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] privateKeyBytes = base64ToBytes(privateKeyBase64);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            return null;
        }
    }

    public static byte[] encryptString(String plaintext, String publicKeyBase64) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, base64ToPublicKey(publicKeyBase64));
            return cipher.doFinal(plaintext.getBytes());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            return null;
        }
    }

    public static String decryptBytes(byte[] ciphertext, String privateKeyBase64) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, base64ToPrivateKey(privateKeyBase64));
            return new String(cipher.doFinal(ciphertext));
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException ex) {
            return null;
        }
    }

    public static void main(String[] args) {
    }
}