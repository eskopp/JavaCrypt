package javacrypt;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MainTest {

    @TempDir
    Path dir;

    private final PrintStream originalErr = System.err;
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @BeforeEach
    void captureErr() {
        System.setErr(new PrintStream(err, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreErr() {
        System.setErr(originalErr);
    }

    private String errText() {
        return new String(err.toByteArray(), StandardCharsets.UTF_8);
    }

    @Test
    void noArgumentsPrintsUsageAndFails() throws Exception {
        assertEquals(1, MyCryptMain.execute(new String[0]));
        assertTrue(errText().contains("Usage:"));
    }

    @Test
    void unknownCommandPrintsUsageAndFails() throws Exception {
        assertEquals(1, MyCryptMain.execute(new String[]{"-bogus"}));
        assertTrue(errText().contains("Usage:"));
    }

    @Test
    void fullCycleThroughMain() throws Exception {
        Path priv = dir.resolve("priv.key");
        Path pub = dir.resolve("pub.key");
        Path in = dir.resolve("in.txt");
        Path enc = dir.resolve("enc.dat");
        Path dec = dir.resolve("dec.txt");
        byte[] plain = "A text that is clearly longer than one hundred bytes so that several RSA blocks are needed for it. Yes."
            .getBytes(StandardCharsets.UTF_8);
        Files.write(in, plain);

        assertEquals(0, MyCryptMain.execute(new String[]{"-genkeys", priv.toString(), pub.toString()}));
        assertEquals(0, MyCryptMain.execute(new String[]{"-encrypt", pub.toString(), in.toString(), enc.toString()}));
        assertEquals(0, MyCryptMain.execute(new String[]{"-decrypt", priv.toString(), enc.toString(), dec.toString()}));

        assertArrayEquals(plain, Files.readAllBytes(dec));
    }

    @Test
    void copyThroughMain() throws Exception {
        Path in = dir.resolve("in.txt");
        Path out = dir.resolve("out.txt");
        Files.write(in, new byte[]{1, 2, 3});

        assertEquals(0, MyCryptMain.execute(new String[]{"-copy", in.toString(), out.toString()}));

        assertArrayEquals(new byte[]{1, 2, 3}, Files.readAllBytes(out));
    }
}
