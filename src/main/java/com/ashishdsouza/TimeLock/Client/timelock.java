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
import java.util.regex.Pattern;

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
        }
        catch(NoSuchAlgorithmException | InvalidKeySpecException ex) {
            return null;
        }
    }

    public static byte[] encryptString(String plaintext, String publicKeyBase64) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, base64ToPublicKey(publicKeyBase64));
            return cipher.doFinal(plaintext.getBytes());
        }
        catch(NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            return null;
        }
    }

    public static String decryptBytes(byte[] ciphertext, String privateKeyBase64) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, base64ToPrivateKey(privateKeyBase64));
            return new String(cipher.doFinal(ciphertext));
        }
        catch(NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException ex) {
            return null;
        }
    }

    public static String help(String version) {
        String stdout = "";
        stdout += "TimeLock v" + version + "\n";
        stdout += "Usage: timelock <action> <file> [options]\n\n";
        stdout += "Action\tDescription";
        stdout += "encrypt\tEncrypt a file\n";
        stdout += "decrypt\tDecrypt a file\n\n";
        stdout += "Option\tLong Option\tDescription\n";
        stdout += "-h\t--help\tShow this help screen\n";
        stdout += "-v\t--version\tShow program version information\n";
        return stdout;
    }

    public static void main(String[] args) {
        String version = "1.0.0";
        if(args.length == 0) {
            // Help
            System.out.println(help(version));
            return;
        }



        if(args.length == 1) {
            if(args[0].replaceAll(Pattern.quote("-"), "").equals("v"))
            switch(args[0]) {
                case "-v":
                case "--version":
                    // Display version info
                    System.out.println("timelock v1.0.0");
                    break;
                default:
                    System.out.println("Unknown action: " + args[0]);
                case "h":
                case "help":
                    // Display help
                    System.out.println(help(version));
                    break;
            }
            return;
        }

        if(args[0].equals("encrypt")) {
            // Encrypt file
        }
        else if(args[0].equals("decrypt")) {
            // Decrypt file
        }
        else {
            System.out.println(help(version));
            return;
        }
    }
}