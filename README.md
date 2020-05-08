# Timelock

A time-lock encryption program, which relies on a verified time server source to prevent falsified decryption attempts.

## What is Time-Lock Encryption?

Time-lock encryption is the concept of ciphertext or an encrypted file that cannot be decrypted until a certain time is reached. Modern cryptographic algorithms are highly sophisticated, yet are made solely with the intent of encryption/decryption with just a static key pair (for asymmetric encryption) or secret key (for symmetric encryption). Unfortunately, these cryptographic algorithms are not time-aware, so for such an implementation to be successful, a secure time-aware third party is needed in this process. The third-party must be virtually impenetrable, with security of the utmost importance. Unfortunately, a major drawback of this time-lock encryption implementation is that the encryption is easily breakable given access to the third-party, which is not an issue with normal cryptographic algorithms.

## Developers
* Ashish D'Souza - [computer-geek64](https://github.com/computer-geek64/)

## Releases
Timelock is currently under production, and does not have any releases yet.

## Versioning
This project is uses the [https://git-scm.com/](Git) Version Control System (VCS).

## License
This project is licensed under the [MIT License](LICENSE).
