package com.ashishdsouza.TimeLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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

            RSAEncryption rsa = new RSAEncryption();

            EncryptionInformation encryptionInformation = new EncryptionInformation();
            String base64PrivateKey = Base64.getEncoder().encodeToString(rsa.getPrivateKey().getEncoded());
            encryptionInformation.setPrivateKey(base64PrivateKey);
            encryptionInformation.setTimestamp(timestamp);
            encryptionInformationRepository.save(encryptionInformation);

            return Base64.getEncoder().encodeToString(rsa.getPublicKey().getEncoded());
        }
        catch(NumberFormatException ex) {
            return "Invalid timestamp value";
        }
        catch(NoSuchAlgorithmException ex) {
            return "No such algorithm";
        }
    }

    @RequestMapping(path = "/checksum/{key}", method = RequestMethod.POST)
    public String updateChecksum(@PathVariable(name = "key") String key, @RequestParam(name = "checksum") String checksum) {
        return "";
    }
}
