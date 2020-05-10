# TimeLock

A database-oriented implementation of time-lock encryption, which relies on a secure, verified third party to prevent falsified decryption attempts.

## What is Time-Lock Encryption?

Time-lock encryption is the concept of ciphertext or an encrypted file that cannot be decrypted until a certain time is reached. Modern cryptographic algorithms are highly sophisticated, yet are made solely with the intent of encryption/decryption with only a static key pair (for asymmetric encryption) or secret key (for symmetric encryption). Unfortunately, these cryptographic algorithms are not time-aware, so for such an implementation to be successful, a secure time-aware third party is needed in this process. The third party must be virtually impenetrable, with security of the utmost importance. Unfortunately, a major drawback of this time-lock encryption implementation is that the encryption is easily breakable if access to the third party is obtained, which is not an issue with regular cryptographic algorithms.

## Cryptographic Keys

Symmetric and asymmetric encryption algorithms are both often used in the digital age. Symmetric encryption involves a singular key (known as a secret key) that is used for both encryption of plaintext and decryption of ciphertext. The primary disadvantage of this form of encryption is that the entities communicating must privately and securely exchange the secret key, prior to the information transaction. As a result, this is not ideal for many forms of communication (where the two parties have no chance to meet previously). This type of encryption is often used for protecting secrets that need to be stored offsite (for instance, on a flash drive in a safe). Asymmetric encryption, on the other hand, involves a key pair consisting of a public key and a private key. The public key can be openly displayed, without risk of revealing non-trivial information, and is often used as an identifier. The public key is used only for encryption of plaintext, and cannot be used to decrypt the ciphertext or derive information about the private key. The private key must be protected and kept hidden, similar to the secret key in symmetric encryption. The private key is used only for decryption of ciphertext. However, it can be used to generate the public key, because the public key and the private key are mathematically related (the public key is derived from the private key), and there is only one private key for every public key (and vice versa). This is a very important aspect of asymmetric encryption. Thus, the private key can be indirectly used to encrypt plaintext.

## Encryption Algorithm

TimeLock relies on asymmetric **RSA 2048-bit** encryption. RSA (Rivest–Shamir–Adleman) is the first encryption algorithm in public-key cryptography. It was invented as a secure alternative to symmetric encryption for effective communication. **RSA 2048-bit** encryption was chosen because it offers the optimal tradeoff between security and memory. **RSA 512-bit** encryption is the most memory-efficient option, but it was considered "broken" in 1999 in under 6 months. **RSA 1024-bit** is still considered relatively secure, but for highly sensitive information and long-term storage, it is not secure enough. Thus, **RSA 2048-bit** is best choice for this case. Furthermore, by using an asymmetric encryption algorithm, the encryption and decryption process can be separated into a public and private key pair. This way, the client program can communicate with the server to encrypt files securely, requiring only the public key, so that the private key is never exposed (preventing the encrypted file from being decrypted prematurely). On the other hand, if a symmetric encryption algorithm were chosen, then the secret key would be exposed during the encryption process. Combined with an intercepting proxy, the key could be extracted to prematurely decrypt the file, rendering the program obsolete.

## Database-Oriented Implementation

Time-lock encryption still remains a theoretical subject. A virtually impenetrable implementation (not requiring a verified third party) has yet to be designed. Until then, the use of a third party is the closest to true time-lock encryption. This implementation of time-lock encryption relies on a secure database within the third party to secure the necessary information required for decryption. This information includes the public key (stored for initial identification purposes, as well as secure indexing without revealing the private key), private key (necessary for decrypting the time-locked file at the appropriate time), release time (in order to identify when the file is ready for decryption), and the checksum of the encrypted file (used to associate the key pair and release time with the corresponding encrypted file). An API server is then used for protected CRUD operations to the database, which is interfaced by the client CLI program.

### Encryption Process:

1. The client program makes a `POST` request to the `/generate` API endpoint, containing the release time as a timestamp in the request body.
2. The API receives the release time and generates an asymmetric **RSA 2048-bit** key pair.
3. The database model then initiates a `CREATE` operation, storing the key pair (encoded as base 64 in a string) and release time. The checksum field is left as null temporarily.
4. The API returns a response containing the public key in the response body
5. The client program receives the public key and uses it to encrypt the specified file.
6. The client program generates an **SHA-256** checksum of the encrypted file and makes another `POST` request to the `/checksum` API endpoint, containing the checksum of the encrypted file and the public key (to identify the correct database entry) in the request body.
7. The API receives the **SHA-256** checksum of the encrypted file and public key.
8. The database model finally initiates an `UPDATE` operation, storing the checksum (only if the field is null, to prevent future overwriting) in the entry containing the correct public key.

### Decryption Process:
1. The client program makes a `GET` request to the `/decrypt` API endpoint, containing an **SHA-256** checksum of the encrypted file in the request URL.
2. The API receives the checksum and initiates a `READ` operation to find the corresponding entry in the database.
3. If the entry does not exist, or if the release time has not yet been reached, then the API returns a response containing an invalid message. This prevents a brute force attack from identifying potential files to decrypt.
4. If the entry does exist, the API returns a response containing the private key in the response body.
5. The database model then initiates a `DELETE` operation to eliminate the entry from the database, in order to optimize space.
6. The client program receives the private key and uses it to decrypt the specified file.

## Software

* [Java SE 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Maven](https://maven.apache.org/)
* [Java Persistence API (JPA)](https://www.oracle.com/java/technologies/persistence-jsp.html)
* [PostgreSQL](https://www.postgresql.org/)

## Documentation

### Prerequisites

This project is managed by [Maven](https://maven.apache.org/). You can run the binaries without installing Maven, but it is needed if you want to rebuild the project (or change the default configuration).

The project assumes that [PostgreSQL](https://www.postgresql.org/) is installed and running. The API is configured to run using PostgreSQL, but can be reconfigured to use other database programs (such as MySQL, SQLite, etc.) by editing the `application.properties` file and installing the necessary dependencies.

## Developers

* Ashish D'Souza - [computer-geek64](https://github.com/computer-geek64/)

## Releases

TimeLock is currently under production, and does not have any releases yet.

## Versioning

This project is uses the [Git](https://git-scm.com/) Version Control System (VCS).

## License

This project is licensed under the [MIT License](LICENSE).
