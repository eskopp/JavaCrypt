# Java Crypt


## Install Java
The first thing to do is to install java. This works differently with each operating system. Here is the instruction for Ubuntu
```shell
sudo apt update -y 
sudo apt-get install -y openjdk-17-jre 
```

## Build Project
The commands for Java are platform independent. Therefore, it doesn't matter if you run it on Linux or Windows.

### Build Class
The command ``javac -Xlint:unchecked ./javacrypt/*.java`` compiles all the Java files in the ``./javacrypt`` directory with the ``-Xlint:unchecked`` option to display warnings for unchecked operations.

```shell
javac -Xlint:unchecked ./javacrypt/*.java
```


### Build  JAR-File
The command ``jar cfm MyCrypt.jar ./makefile.txt javacrypt/*.class`` creates a JAR (Java Archive) file named ``MyCrypt.jar`` with the specified manifest file (``makefile.txt``) and includes all the class files (``*.class``) in the ``javacrypt`` directory.

```shell
 jar cfm MyCrypt.jar manifest.txt javacrypt/*.class
```

## Usage

### Start Programm
```shell
$ java -jar JavaCrypt.jar
Program 'MyCryptMain'
usage:
        MyCryptMain -genkeys [priv_keyfile] [pub_keyfile]
        MyCryptMain -encrypt [pub_keyfile] [ifile] [ofile]
        MyCryptMain -decrypt [privkeyfile] [ifile] [ofile]
        MyCryptMain -copy [dummyword] [ifile]  [ofile]
```

### Generate Keys 
Before you can use the encryption, you need to generate it with keys. The keys are stored in binary form. Therefore, they cannot be opened with a normal editor. The public key is needed for encryption and the private key for decryption. 

```shell
$ java -jar JavaCrypt.jar -genkeys
Class found : javacrypt.RunGenKeys
Package     : package javacrypt
RunGenkeys
Private      Schluesseldatei : priv.key
Oeffentliche Schluesseldatei : pub.key
Ende des Programms.
```

### Encrypt Files 
For the encryption you need three files. A public key, the file to be encrypted and the output file. With the flag "-encrypt" and the correct specification of the files the file will be encrypted.
```shell
$ java -jar JavaCrypt.jar -encrypt ./pub.key text.txt test_encrypt.txt
Class found : javacrypt.RunEncrypt
Package     : package javacrypt
RunEncrypt
Anzahl der uebertragenen Bytes=600
Ende des Programms.
```


### Decrypt Files 
For decryption, you need three files. A private key, the file to be decrypted and the output file. By the flag "-decrypt" and the correct specification of the files the file is decrypted. 
```shell
$ java -jar JavaCrypt.jar -decrypt ./priv.key  test_encrypt.txt test_decrypt.txt
Class found : javacrypt.RunDecrypt
Package     : package javacrypt
RunDecrypt
Anzahl der uebertragenen Bytes=768
Ende des Programms.
```


### Copy Files
The copying of files is a function that has arisen more through the others. For this reason one must also specify a "dummy word". But it doesn't matter which string you enter, because it will be ignored. 

```shell
$ java -jar JavaCrypt.jar -copy dummy text.txt text_copy.txt
Class found : javacrypt.RunCopy
Package     : package javacrypt
RunCopy
Anzahl der uebertragenen Bytes=600
Ende des Programms.
```

## Program architecture
Here is the UML plan of the project. 
<img src="./img/UML.png" alt="Java UML">

