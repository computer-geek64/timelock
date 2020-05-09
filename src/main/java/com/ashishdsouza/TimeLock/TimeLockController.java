package com.ashishdsouza.TimeLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

@RestController
public class TimeLockController {
    @Autowired
    private EncryptionInformationRepository encryptionInformationRepository;

    @RequestMapping(path = "/generate", method = RequestMethod.POST)
    public String generate(@RequestParam(name = "time", defaultValue = "0") String time) {
        // Return public key
        try {
            if (time.split(Pattern.quote("."))[0].length() < 10) {
                throw new NumberFormatException();
            }

            double timestamp = Double.parseDouble(time);

            RSAEncryptionKeys rsaEncryptionKeys = new RSAEncryptionKeys();

            EncryptionInformation encryptionInformation = new EncryptionInformation();
            String base64PublicKey = rsaEncryptionKeys.getPublicKeyBase64();
            String base64PrivateKey = rsaEncryptionKeys.getPrivateKeyBase64();
            encryptionInformation.setPublicKey(base64PublicKey);
            encryptionInformation.setPrivateKey(base64PrivateKey);
            encryptionInformation.setTimestamp(timestamp);
            encryptionInformationRepository.save(encryptionInformation);

            return base64PublicKey;
        }
        catch(NumberFormatException ex) {
            return "Invalid timestamp value";
        }
        catch(NoSuchAlgorithmException ex) {
            return "No such algorithm";
        }
    }

    @RequestMapping(path = "/checksum", method = RequestMethod.POST)
    public String updateChecksum(@RequestParam(name = "key") String key, @RequestParam(name = "checksum") String checksum) {
        if(encryptionInformationRepository.findById(key).isEmpty()) {
            return "Invalid public key";
        }

        EncryptionInformation encryptionInformation = encryptionInformationRepository.findById(key).get();

        if(encryptionInformation.getChecksum() != null) {
            return "Invalid public key";
        }

        encryptionInformation.setChecksum(checksum);
        encryptionInformationRepository.save(encryptionInformation);
        return "Checksum saved";
    }
}
