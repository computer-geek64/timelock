package com.ashishdsouza.TimeLock;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EncryptionInformationRepository extends CrudRepository<EncryptionInformation, String> {
}