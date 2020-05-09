package com.ashishdsouza.TimeLock;

import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
public class TimeLockController {
    @RequestMapping(path = "/generate", method = RequestMethod.POST)
    public String generate(@RequestParam(name = "time", defaultValue = "0") String time) {
        // Return public key
        try {
            if (time.split(Pattern.quote("."))[0].length() < 10) {
                throw new NumberFormatException();
            }

            double timestamp = Double.parseDouble(time);
        } catch(NumberFormatException ex) {
            return "Invalid timestamp value";
        }
        return "";
    }

    @RequestMapping(path = "/checksum/{key}", method = RequestMethod.POST)
    public String updateChecksum(@PathVariable(name = "key") String key, @RequestParam(name = "checksum") String checksum) {
        return "";
    }
}
