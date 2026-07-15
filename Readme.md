# Java Crypt


## Install Java
The only thing you need to install yourself is a JDK (11 or newer).

- **Debian**: `sudo apt update && sudo apt install default-jdk`
- **Arch**: `sudo pacman -S jdk-openjdk`
- **Mac** (Homebrew): `brew install openjdk`
- **Windows** (winget): `winget install EclipseAdoptium.Temurin.21.JDK`

## Build Project
This project uses [Maven](https://maven.apache.org/) together with the Maven Wrapper, so you don't need
to install Maven yourself - the wrapper downloads it for you on first run.

```bash
./mvnw package      # Linux / Mac
mvnw.cmd package    # Windows
```

This produces `target/JavaCrypt.jar`.

## Run JavaCrypt

### Start Programm
```shell
java -jar target/JavaCrypt.jar
```

### Generate Keys 
Before you can use the encryption, you need to generate it with keys. The keys are stored in binary form. Therefore, they cannot be opened with a normal editor. The public key is needed for encryption and the private key for decryption. 

```shell
java -jar target/JavaCrypt.jar -genkeys <PUBLIC-KEY> <PRIVATE-KEY>
```

### Encrypt Files 
For the encryption you need three files. A public key, the file to be encrypted and the output file. With the flag "-encrypt" and the correct specification of the files the file will be encrypted.
```shell
java -jar target/JavaCrypt.jar -encrypt <PUBLIC-KEY> <FILE> <ENCRYPTED-FILE>
```


### Decrypt Files 
For decryption, you need three files. A private key, the file to be decrypted and the output file. By the flag "-decrypt" and the correct specification of the files the file is decrypted. 
```shell
java -jar target/JavaCrypt.jar -decrypt <PRIVATE-KEY>  <ENCRYPTED-FILE> <DECRYPT-FILE> 
```


### Copy Files
A simple file copy, without encryption.

```shell
java -jar target/JavaCrypt.jar -copy text.txt text_copy.txt
```

## Full Example
An end-to-end walkthrough, from a fresh clone to a verified encrypt/decrypt roundtrip:

```bash
./mvnw package

java -jar target/JavaCrypt.jar -genkeys priv.key pub.key
# Private key file: priv.key
# Public key file:  pub.key

echo "Hello World" > test.txt

java -jar target/JavaCrypt.jar -encrypt pub.key test.txt test_encrypt.txt
# Number of bytes transferred: 128

java -jar target/JavaCrypt.jar -decrypt priv.key test_encrypt.txt test_decrypt.txt
# Number of bytes transferred: 128

cat test_decrypt.txt
# Hello World
```

