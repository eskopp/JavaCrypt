/*
 * File: MyCryptMain.java
 * Package: javacrypt
 * Author: Erik Skopp
 * Created: 10.06.2018
 * Modified: 18.11.2023
 * Version: 1.0.4
 */

package javacrypt;

import java.util.Arrays;
import java.util.List;

/**
 * The main class for the MyCrypt program.
 * This class serves as the entry point and controls the execution of various cryptography functions.
 * The program supports key pair generation, encryption, decryption, and file copying.
 * <p>
 * Command-line arguments:
 * -genkeys [priv_keyfile] [pub_keyfile]: Generates a key pair and stores it in the specified files.
 * -encrypt [pub_keyfile] [ifile] [ofile]: Encrypts the input file using the public key and saves the result to the output file.
 * -decrypt [privkeyfile] [ifile] [ofile]: Decrypts the input file using the private key and saves the result to the output file.
 * -copy [dummyword] [ifile] [ofile]: Copies the input file to the output file (without encryption).
 */
public class MyCryptMain {

    private static final String PROG_NAME = "JavaCrypt";

    // Mapping of command and class
    private static final List<String[]> MY_ARRAY = Arrays.asList(
        new String[]{"-genkeys", "javacrypt.RunGenKeys"},
        new String[]{"-encrypt", "javacrypt.RunEncrypt"},
        new String[]{"-decrypt", "javacrypt.RunDecrypt"},
        new String[]{"-copy", "javacrypt.RunCopy"}
    );

    /**
     * Displays the usage of the program and exits.
     */
    private static void usage() {
        String[] msg = {
            "Program '" + PROG_NAME + "'",
            "Usage:",
            "\t" + PROG_NAME + " " + MY_ARRAY.get(0)[0] + " [priv_keyfile] [pub_keyfile]",
            "\t" + PROG_NAME + " " + MY_ARRAY.get(1)[0] + " [pub_keyfile] [ifile] [ofile] ",
            "\t" + PROG_NAME + " " + MY_ARRAY.get(2)[0] + " [privkeyfile] [ifile] [ofile] ",
            "\t" + PROG_NAME + " " + MY_ARRAY.get(3)[0] + " [dummyword] [ifile]  [ofile] ",
            ""
        };

        // Output the strings
        Arrays.stream(msg).forEach(System.err::println);

        // Exit the program with an error code
        System.exit(0);
    }

    /**
     * Entry point of the main program.
     *
     * @param args The command-line arguments.
     * @throws Exception If an error occurs.
     */
    public static void main(String[] args) throws Exception {
        // Factory instance
        RunnableKeyValueFactory runnableKeyValueFactory = new RunnableKeyValueFactory(MY_ARRAY.toArray(new String[0][]));

        // Get the number of command-line arguments
        int argSize = args.length;

        if (argSize == 0) {
            usage(); // Static method
        }

        // Get the control argument
        String cmdKey = args[0];

        // Check if the command exists
        if (!runnableKeyValueFactory.containsKey(cmdKey)) {
            usage();
        }

        // Perform the shift operation
        List<String> optArgList = Arrays.asList(args).subList(1, args.length);

        // Instantiation using the factory method:
        RunnableInterface myRun = runnableKeyValueFactory.getInstanceFromKey(cmdKey);

        // Call the run method
        myRun.run(optArgList);

        System.out.println("End of the program.");
    }
}
