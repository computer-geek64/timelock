package com.ashishdsouza.TimeLock;

import org.springframework.data.repository.CrudRepository;
import com.ashishdsouza.TimeLock.EncryptionInformation;

public interface EncryptionInformationRepository extends CrudRepository<EncryptionInformation, String> {
}