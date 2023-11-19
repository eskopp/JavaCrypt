/*
 * File: RunEncrypt.java
 * Package: javacrypt
 * Author: Erik Skopp
 * Created: 10.06.2018
 * Modified: 18.11.2023
 * Version: 1.0.4
 */

package javacrypt;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.nio.file.Files;

/**
 * This class represents the RunEncrypt command, which performs RSA encryption using the public key.
 * It extends the RunnableBase class.
 *
 * Command-line arguments:
 * [pubkeyfile] [ifile] [ofile]: Encrypts the input file using the public key and saves the result to the output file.
 */
public class RunEncrypt extends RunnableBase {

    // Default public key file name if not specified through the command line
    public static final String DEFAULT_KEY_FILE = "pub.key";

    // Default input file name if not specified through the command line
    public static final String DEFAULT_IFILE = "encr_inp.dat";

    // Default output file name if not specified through the command line
    public static final String DEFAULT_OFILE = "encr_outp.dat";

    // Size of the crypto buffer
    public static final int RSA_ENCRYPT_BUFSIZE = 100;

    /**
     * Constructor
     */
    public RunEncrypt() {
    }

    /**
     * Returns the size of the crypto buffer.
     *
     * @return The buffer size.
     */
    public int getCryptoBufSize() {
        return RSA_ENCRYPT_BUFSIZE;
    }

    /**
     * Encrypts the given text using the provided public key and cipher.
     *
     * @param text   The original unencrypted text.
     * @param key    The public key.
     * @param cipher The cipher.
     * @return The encrypted text.
     * @throws Exception If an error occurs during encryption.
     */
    public byte[] crypt(byte[] text, Key key, Cipher cipher) throws Exception {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, (PublicKey) key);
            return cipher.doFinal(text);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Returns the cipher for encryption.
     *
     * @param key The public key.
     * @return The cipher for encryption.
     */
    @Override
    protected Cipher getCipher(Key key) {
        try {
            PublicKey pubKey = (PublicKey) key;
            Cipher cipher = Cipher.getInstance(CRYPTO_ALGORITHMUS);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            System.err.println("Exception: " + ex.getMessage());
            System.exit(8);
            return null; // Unreachable code, added to satisfy the compiler
        }
    }

    /**
     * Executes the RunEncrypt command.
     *
     * @param args The command-line arguments.
     */
    @Override
    public void run(List<String> args) {
        System.out.println("RunEncrypt");

        int size = 0;

        // Input file
        File publicKeyFile = null;

        // Input file
        File inputFile = null;

        // Output file
        File outputFile = null;

        if (!args.isEmpty()) {
            publicKeyFile = new File(args.get(0));
        } else {
            publicKeyFile = new File(DEFAULT_KEY_FILE);
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

        PublicKey pubKey = null;
        try {
            pubKey = (PublicKey) getKeyObjectFromFile(publicKeyFile);
            size = encryptDecryptFile(pubKey, inputFile, outputFile);
        } catch (Exception ex) {
            System.err.println("EXCEPTION: run : " + ex.getMessage());
        }

        System.out.println("Number of bytes transferred: " + size);
    }

    /**
     * Encrypts the input file using the public key and saves the result to the output file.
     *
     * @param publicKey The public key.
     * @param inputFile The input file.
     * @param outputFile The output file.
     * @return The number of bytes transferred.
     * @throws Exception If an error occurs during encryption.
     */
    private int encryptDecryptFile(PublicKey publicKey, File inputFile, File outputFile) throws Exception {
        try {
            Cipher cipher = getCipher(publicKey);
            byte[] inputBytes = Files.readAllBytes(inputFile.toPath());
            byte[] encryptedBytes = crypt(inputBytes, publicKey, cipher);
            Files.write(outputFile.toPath(), encryptedBytes);
            return encryptedBytes.length;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw e;
        }
    }
}
