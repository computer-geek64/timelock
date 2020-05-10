package com.ashishdsouza.TimeLock.Client;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

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

    public static String getRequest(String url, HashMap<String, String> values) {
        StringBuilder argsStringBuilder = new StringBuilder("?");

        for(String key : values.keySet()) {
            argsStringBuilder.append(key).append("=").append(values.get(key)).append("&");
        }

        String args = argsStringBuilder.toString();
        args = args.substring(0, args.length() - 1);

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url + args))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        }
        catch(IOException | InterruptedException ex) {
            return null;
        }
    }

    public static String postRequest(String url, HashMap<String, String> values) {
        try {
            StringBuilder requestBodyBuilder = new StringBuilder();
            for(String key : values.keySet()) {
                requestBodyBuilder.append(URLEncoder.encode(key, StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(values.get(key), StandardCharsets.UTF_8)).append("&");
            }
            String requestBody = requestBodyBuilder.toString();
            requestBody = requestBody.substring(0, requestBody.length() - 1);

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        }
        catch(IOException | InterruptedException ex) {
            return null;
        }
    }

    public static String help(String version) {
        String stdout = "";
        stdout += "TimeLock v" + version + "\n";
        stdout += "Usage: timelock <encrypt|decrypt> <file> [options]\n\n";
        stdout += "Option\tLong Option\tDescription\n";
        stdout += "-h\t--help\tShow this help screen\n";
        stdout += "-v\t--version\tShow program version information\n";
        return stdout;
    }

    public static void main(String[] args) {
        String version = "1.0.0";
        if (args.length == 0) {
            // Help
            System.out.println(help(version));
            return;
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "-v":
                case "--version":
                    // Display version info
                    System.out.println("TimeLock v" + version);
                    break;
                default:
                    System.out.println("Unknown action: " + args[0]);
                case "-h":
                case "--help":
                    // Display help
                    System.out.println(help(version));
                    break;
            }
            return;
        }

        if (args[0].equals("encrypt")) {
            // Encrypt file
            File file = new File(args[1]);
            if(!file.exists() || file.isDirectory()) {
                System.out.println("Invalid file");
                return;
            }

            double timestamp = (double) System.currentTimeMillis() / 1000 + 120;

            HashMap<String, String> postArgs = new HashMap<>();
            postArgs.put("time", String.format("%f", timestamp));

            String publicKeyBase64 = postRequest("http://localhost:8080/generate", postArgs);

            try {
                byte[] fileBytes = Files.readAllBytes(file.toPath());
                byte[] ciphertext = encryptString(bytesToBase64(fileBytes), publicKeyBase64);
                OutputStream outputStream = new FileOutputStream(new File(file.toPath() + ".enc"));
                outputStream.write(ciphertext);
                outputStream.close();
            } catch (IOException e) {
                System.out.println("Error reading file: " + file.toPath());
                return;
            }
        } else if (args[0].equals("decrypt")) {
            // Decrypt file
            File file = new File(args[1]);
            if(!file.exists() || file.isDirectory()) {
                System.out.println("Invalid file");
                return;
            }

            return;
        } else {
            System.out.println(help(version));
        }
    }
}