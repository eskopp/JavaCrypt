/*
 * File: RunDecrypt.java
 * Package: javacrypt
 * Author: Erik Skopp
 * Created: 10.06.2018
 * Modified: 18.11.2023
 * Version: 1.0.4
 */

package javacrypt;

import java.util.List;
import java.io.File;
import java.security.Key;
import java.security.PrivateKey;
import javax.crypto.Cipher;

/**
 * This class represents the RunDecrypt command, which performs RSA decryption using the private key.
 * It extends the RunnableBase class.
 *
 * Command-line arguments:
 * [privkeyfile] [ifile] [ofile]: Decrypts the input file using the private key and saves the result to the output file.
 */
public class RunDecrypt extends RunnableBase {

    // Default private key file name if not specified through the command line
    public static final String DEFAULT_KEY_FILE = "priv.key";

    // Default input file name if not specified through the command line
    public static final String DEFAULT_IFILE = "decrypt_inp.dat";

    // Default output file name if not specified through the command line
    public static final String DEFAULT_OFILE = "decrypt_outp.dat";

    // Size of the crypto buffer
    public static final int RSA_DECRYPT_BUFSIZE = 128;

    /**
     * Returns the size of the crypto buffer.
     *
     * @return The buffer size.
     */
    public int getCryptoBufSize() {
        return RSA_DECRYPT_BUFSIZE;
    }

    /**
     * Decrypts the given text using the provided private key and cipher.
     *
     * @param text   The encrypted bytes.
     * @param key    The private key.
     * @param cipher The cipher.
     * @return The decrypted bytes.
     * @throws Exception If an error occurs during decryption.
     */
    public byte[] crypt(byte[] text, Key key, Cipher cipher) throws Exception {
        byte[] decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, (PrivateKey) key);
            decryptedText = cipher.doFinal(text);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw e;
        }
        return decryptedText;
    }

    /**
     * Executes the RunDecrypt command.
     *
     * @param args The command-line arguments.
     */
    @Override
    public void run(List<String> args) {
        int size = 0;
        System.out.println("RunDecrypt");

        // Input file
        File privateKeyFile = null;

        // Input file
        File inputFile = null;

        // Output file
        File outputFile = null;

        if (!args.isEmpty()) {
            privateKeyFile = new File(args.get(0));
        } else {
            privateKeyFile = new File(DEFAULT_KEY_FILE);
        }

        if (args.size() > 1) {
            inputFile = new File(args.get(1));
        } else {
            inputFile = new File(DEFAULT_IFILE);
        }

        if (args.size() > 2) {
            outputFile = new File(args.get(2));
        } else {
            outputFile = new File(DEFAULT_OFILE);
        }

        try {
            PrivateKey privateKey = (PrivateKey) getKeyObjectFromFile(privateKeyFile);
            size = encryptDecryptFile(privateKey, inputFile, outputFile);
        } catch (Exception ex) {
            System.err.println("EXCEPTION: run : " + ex.getMessage());
        }

        System.out.println("Number of bytes transferred: " + size);
    }

    /**
     * Returns the cipher for decryption.
     *
     * @param key The private key.
     * @return The cipher for decryption.
     */
    @Override
    protected Cipher getCipher(Key key) {
        Cipher cipher = null;
        try {
            PrivateKey privKey = (PrivateKey) key;
            cipher = Cipher.getInstance(CRYPTO_ALGORITHMUS);
            cipher.init(Cipher.DECRYPT_MODE, privKey);
        } catch (Exception ex) {
            System.err.println("EXCEPTION: getCipher : " + ex.getMessage());
        }
        return cipher;
    }
}
