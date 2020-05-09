package com.ashishdsouza.TimeLock;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EncryptionInformation {
    @Id
    private String privateKey;
    private Double timestamp;
    private String checksum;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
