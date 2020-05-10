# TimeLock

A database-oriented implementation of time-lock encryption, which relies on a secure, verified third party to prevent falsified decryption attempts.

## What is Time-Lock Encryption?

Time-lock encryption is the concept of ciphertext or an encrypted file that cannot be decrypted until a certain time is reached. Modern cryptographic algorithms are highly sophisticated, yet are made solely with the intent of encryption/decryption with just a static key pair (for asymmetric encryption) or secret key (for symmetric encryption). Unfortunately, these cryptographic algorithms are not time-aware, so for such an implementation to be successful, a secure time-aware third party is needed in this process. The third party must be virtually impenetrable, with security of the utmost importance. Unfortunately, a major drawback of this time-lock encryption implementation is that the encryption is easily breakable if access to the third party is obtained, which is not an issue with regular cryptographic algorithms.

## Cryptographic Keys

Symmetric and asymmetric encryption algorithms are both often used in the digital age. Symmetric encryption involves a singular key (known as a secret key) that is used for both encryption of plaintext and decryption of ciphertext. The primary disadvantage of this form of encryption is that the entities communicating must privately and securely exchange the secret key, prior to the information transaction. As a result, this is not ideal for many forms of communication (where the two parties have no chance to meet previously). This type of encryption is often used for protecting secrets that need to be stored offsite (for instance, on a flash drive in a safe). Asymmetric encryption, on the other hand, involves a key pair consisting of a public key and a private key. The public key can be openly displayed, without risk of revealing non-trivial information, and is often used as an identifier. The public key is used only for encryption of plaintext, and cannot be used to decrypt the ciphertext or derive information about the private key. The private key must be protected and kept hidden, similar to the secret key in symmetric encryption. The private key is used only for decryption of ciphertext. However, it can be used to generate the public key, because the public key and the private key are mathematically related (the public key is derived from the private key), and there is only one private key for every public key (and vice versa). This is a very important aspect of asymmetric encryption. Thus, the private key can be indirectly used to encrypt plaintext.

## Encryption Algorithm

TimeLock relies on asymmetric **RSA 1024-bit** encryption. DESCRIBE RSA ENCRYPTION. By using an asymmetric encryption algorithm, the encryption and decryption process can be separated into a public and private key pair. This way, the client program can communicate with the server to encrypt files securely, requiring only the public key, so that the private key is never exposed (preventing the encrypted file from being decrypted prematurely). On the other hand, if a symmetric encryption algorithm were chosen, then the secret key would be exposed during the encryption process. Combined with an intercepting proxy, the key could be extracted to prematurely decrypt the file, rendering the program obsolete.

## Database-Oriented Implementation

This basic implementation of time-lock encryption primarily consists of developing the secure, verified third party. The third party consists of an API that is requested during encryption and decryption.

### Encryption Process:

1. Client program sends request to server containing the release time
2. Server generates asymmetric key pair, storing the key pair and release time in the database, and returns public key along with a new endpoint for the client program to update the database with new information
3. Client program encrypts file with the received public key
4. Client program sends request to new server endpoint containing the checksum of the encrypted file
5. Server stores the checksum of the encrypted file with the corresponding private key and release time


## Software

* Java
* Spring Boot

## Developers

* Ashish D'Souza - [computer-geek64](https://github.com/computer-geek64/)

## Releases

TimeLock is currently under production, and does not have any releases yet.

## Versioning

This project is uses the [Git](https://git-scm.com/) Version Control System (VCS).

## License

This project is licensed under the [MIT License](LICENSE).
