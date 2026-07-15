/*
 * File: RunGenKeys.java
 * Package: javacrypt
 * Author: Erik Skopp
 * Created: 10.06.2018
 * Modified: 18.11.2023
 * Version: 1.0.4
 */

package javacrypt;

import java.io.*;
import java.security.*;
import java.util.List;
import javax.crypto.Cipher;

/**
 * This class represents the RunGenKeys command, which generates a pair of RSA keys.
 * It extends the RunnableBase class.
 *
 * Command-line arguments:
 * [privkeyfile] [pubkeyfile]: Generates a private key file and a public key file.
 */
public class RunGenKeys extends RunnableBase {

    // Default private key file name if not specified through the command line
    public static final String DEFAULT_FILE_PRIV = "priv.key";

    // Default public key file name if not specified through the command line
    public static final String DEFAULT_FILE_PUB = "pub.key";

    /**
     * Executes the RunGenKeys command.
     *
     * @param args The command-line arguments.
     */
    @Override
    public void run(List<String> args) {
        System.out.println("RunGenKeys");

        // Private key file
        File privateKeyFile;

        // Public key file
        File publicKeyFile;

        if (!args.isEmpty()) {
            privateKeyFile = new File(args.get(0));
        } else {
            privateKeyFile = new File(DEFAULT_FILE_PRIV);
        }

        if (args.size() > 1) {
            publicKeyFile = new File(args.get(1));
        } else {
            publicKeyFile = new File(DEFAULT_FILE_PUB);
        }

        try {
            // Generate key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(CRYPTO_ALGORITHMUS);
            keyPairGenerator.initialize(KEY_LENGTH);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // Write private key to file
            writeKeyToFile(privateKey, privateKeyFile);

            // Write public key to file
            writeKeyToFile(publicKey, publicKeyFile);

            System.out.println("Private key file: " + privateKeyFile.getCanonicalPath());
            System.out.println("Public key file: " + publicKeyFile.getCanonicalPath());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("The algorithm '" + CRYPTO_ALGORITHMUS + "' is not implemented!");
            System.exit(2);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Writes the given key to a file.
     *
     * @param key  The key to write.
     * @param file The file to write the key to.
     */
    private void writeKeyToFile(Key key, File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(key);
        } catch (IOException e) {
            System.err.println("Exception(writeKeyToFile): " + e.getMessage());
            System.exit(2);
        }
    }

    /**
     * Returns the cipher.
     *
     * @return The cipher.
     */
    @Override
    protected Cipher getCipher(Key key) {
        return null;
    }

    /**
     * Encrypts or decrypts the given text using the provided key and cipher.
     *
     * @param text   The text to encrypt or decrypt.
     * @param key    The key.
     * @param cipher The cipher.
     * @return The encrypted or decrypted text.
     * @throws Exception If an error occurs during encryption or decryption.
     */
    @Override
    public byte[] crypt(byte[] text, Key key, Cipher cipher) throws Exception {
        throw new UnsupportedOperationException("Not supported.");
    }

    /**
     * Returns the size of the crypto buffer.
     *
     * @return The buffer size.
     */
    @Override
    public int getCryptoBufSize() {
        return 100;
    }
}
