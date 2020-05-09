package com.ashishdsouza.TimeLock;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EncryptionInformation {
    @Id
    private String privateKey;
    private Integer timestamp;
    private String checksum;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
