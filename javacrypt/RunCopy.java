/*
 * File: RunCopy.java
 * Package: javacrypt
 * Author: Erik Skopp
 * Created: 10.06.2018
 * Modified: 18.11.2023
 * Version: 1.0.4
 */

package javacrypt;

import java.io.File;
import java.security.Key;
import java.util.List;

/**
 * This class represents the RunCopy command, which copies the input file to the output file without encryption.
 * It extends the RunnableBase class.
 *
 * Command-line arguments:
 * [ifile] [ofile]: Copies the input file to the output file (without encryption).
 * 
 * Note: The buffer size used for copying is set to 32768 bytes.
 * Adjust the buffer size according to your specific requirements and system resources.
 * 
 * @author Erik Skopp
 */
public class RunCopy extends RunnableBase {

    // Default input file name if not specified through the command line
    public static final String DEFAULT_IFILE = "copy_inp.dat";

    // Default output file name if not specified through the command line
    public static final String DEFAULT_OFILE = "copy_outp.dat";

    // Size of the crypto buffer
    public static final int RSA_COPY_BUFSIZE = 32768;

    /**
     * Constructor
     */
    public RunCopy() {
    }

    /**
     * Returns the size of the crypto buffer.
     *
     * @return The buffer size.
     */
    public int getCryptoBufSize() {
        return RSA_COPY_BUFSIZE;
    }

    /**
     * Encrypts or decrypts the given text using the provided key and cipher.
     * This method returns the input text as is, since RunCopy does not perform any encryption or decryption.
     *
     * @param text   The original unencrypted text.
     * @param key    The encryption/decryption key.
     * @param cipher The cipher.
     * @return The encrypted or decrypted text.
     * @throws Exception If an error occurs during encryption or decryption.
     */
    @Override
    public byte[] crypt(byte[] text, Key key, javax.crypto.Cipher cipher) throws Exception {
        return text;
    }

    /**
     * Returns the cipher.
     * In the case of RunCopy, it returns null since no encryption or decryption is performed.
     *
     * @param key The encryption/decryption key.
     * @return The cipher.
     */
    @Override
    protected javax.crypto.Cipher getCipher(Key key) {
        return null;
    }

    /**
     * Executes the RunCopy command.
     *
     * @param args The command-line arguments.
     */
    @Override
    public void run(List<String> args) {
        int size = 0;

        // Input file
        File inputFile;

        // Output file
        File outputFile;

        System.out.println("RunCopy");

        if (!args.isEmpty()) {
            // Use the file specified in the command-line argument as the input file
            inputFile = new File(args.get(0));
        } else {
            // Use the default input file if no command-line argument is provided
            inputFile = new File(DEFAULT_IFILE);
        }

        if (args.size() > 1) {
            // Use the file specified in the command-line argument as the output file
            outputFile = new File(args.get(1));
        } else {
            // Use the default output file if no command-line argument is provided
            outputFile = new File(DEFAULT_OFILE);
        }

        try {
            // Perform the file copying operation without encryption
            size = encryptDecryptFile(null, inputFile, outputFile);
        } catch (Exception ex) {
            System.err.println("Exception: " + ex.toString() + " : " + ex.getMessage());
        }
        System.out.println("Number of bytes transferred: " + size);
    }
}
