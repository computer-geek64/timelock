# TimeLock

A time-lock encryption program, which relies on a verified time server source to prevent falsified decryption attempts.

## What is Time-Lock Encryption?

Time-lock encryption is the concept of ciphertext or an encrypted file that cannot be decrypted until a certain time is reached. Modern cryptographic algorithms are highly sophisticated, yet are made solely with the intent of encryption/decryption with just a static key pair (for asymmetric encryption) or secret key (for symmetric encryption). Unfortunately, these cryptographic algorithms are not time-aware, so for such an implementation to be successful, a secure time-aware third party is needed in this process. The third-party must be virtually impenetrable, with security of the utmost importance. Unfortunately, a major drawback of this time-lock encryption implementation is that the encryption is easily breakable if access to the third-party is obtained, which is not an issue with regular cryptographic algorithms.

## Cryptographic Keys

Symmetric and asymmetric encryption algorithms are both used often in the digital age. Symmetric encryption involves a singular key (known as a secret key) that is used for both encryption of plaintext and decryption of ciphertext. The primary disadvantage of this form of encryption is that the entities communicating must privately and securely exchange the secret key, prior to the information transaction. This type of encryption is often used for protecting secrets that need to be stored offsite (for instance, on a flash drive in a safe).

## Encryption Algorithm

The chosen encryption algorithm is still under consideration, but it will most likely be asymmetric RSA encryption.

## Developers

* Ashish D'Souza - [computer-geek64](https://github.com/computer-geek64/)

## Releases

TimeLock is currently under production, and does not have any releases yet.

## Versioning

This project is uses the [Git](https://git-scm.com/) Version Control System (VCS).

## License

This project is licensed under the [MIT License](LICENSE).
