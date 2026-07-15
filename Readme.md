# JavaCrypt

A small command-line tool for RSA file encryption, decryption, key generation, and plain file copying.

## Install Java

The only thing you need to install yourself is a JDK (11 or newer).

- **Debian**: `sudo apt update && sudo apt install default-jdk`
- **Arch**: `sudo pacman -S jdk-openjdk`
- **Mac** (Homebrew): `brew install openjdk`
- **Windows** (winget): `winget install EclipseAdoptium.Temurin.21.JDK`

## Build

This project uses [Maven](https://maven.apache.org/) together with the Maven Wrapper, so you don't need
to install Maven yourself - the wrapper downloads it for you on first run.

```bash
./mvnw package      # Linux / Mac
mvnw.cmd package    # Windows
```

This produces `target/JavaCrypt.jar`.

## Usage

```
java -jar target/JavaCrypt.jar -genkeys [priv_keyfile] [pub_keyfile]
java -jar target/JavaCrypt.jar -encrypt [pub_keyfile] [ifile] [ofile]
java -jar target/JavaCrypt.jar -decrypt [priv_keyfile] [ifile] [ofile]
java -jar target/JavaCrypt.jar -copy [ifile] [ofile]
```

### Generate Keys

Generates an RSA key pair and writes it to the given files, in Java's own serialized object format
(not a standard PEM/DER format, so it's only readable by JavaCrypt itself).

```shell
java -jar target/JavaCrypt.jar -genkeys priv.key pub.key
```

### Encrypt a File

Encrypts a file using a public key.

```shell
java -jar target/JavaCrypt.jar -encrypt pub.key file.txt file.enc
```

### Decrypt a File

Decrypts a file using the matching private key.

```shell
java -jar target/JavaCrypt.jar -decrypt priv.key file.enc file.dec
```

### Copy a File

A plain file copy, without any encryption.

```shell
java -jar target/JavaCrypt.jar -copy file.txt file_copy.txt
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

## License

[GPLv3](LICENSE)
