package javacrypt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ErrorPathTest {

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
    void encryptWithMissingKeyFileReportsErrorAndWritesNothing() {
        Path out = dir.resolve("out.dat");

        new RunEncrypt().run(Arrays.asList(
            dir.resolve("missing.key").toString(), dir.resolve("in.dat").toString(), out.toString()));

        assertTrue(errText().contains("EXCEPTION: run"));
        assertFalse(Files.exists(out));
    }

    @Test
    void decryptWithMissingInputReportsError() {
        Path priv = dir.resolve("priv.key");
        new RunGenKeys().run(Arrays.asList(priv.toString(), dir.resolve("pub.key").toString()));

        new RunDecrypt().run(Arrays.asList(
            priv.toString(), dir.resolve("missing.dat").toString(), dir.resolve("out.dat").toString()));

        assertTrue(errText().contains("EXCEPTION: run"));
    }

    @Test
    void decryptWithWrongKeyFails() throws Exception {
        Path priv1 = dir.resolve("priv1.key");
        Path pub1 = dir.resolve("pub1.key");
        Path priv2 = dir.resolve("priv2.key");
        Path pub2 = dir.resolve("pub2.key");
        new RunGenKeys().run(Arrays.asList(priv1.toString(), pub1.toString()));
        new RunGenKeys().run(Arrays.asList(priv2.toString(), pub2.toString()));
        Path in = dir.resolve("in.txt");
        Path enc = dir.resolve("enc.dat");
        Path dec = dir.resolve("dec.txt");
        Files.write(in, "secret".getBytes(StandardCharsets.UTF_8));

        new RunEncrypt().run(Arrays.asList(pub1.toString(), in.toString(), enc.toString()));
        new RunDecrypt().run(Arrays.asList(priv2.toString(), enc.toString(), dec.toString()));

        assertTrue(errText().contains("Exception"));
        assertEquals(0, Files.exists(dec) ? Files.size(dec) : 0);
    }

    @Test
    void genKeysToUnwritablePathReportsErrorWithoutExit() {
        Path bad = dir.resolve("no-such-dir").resolve("priv.key");

        new RunGenKeys().run(Arrays.asList(bad.toString(), dir.resolve("pub.key").toString()));

        assertTrue(errText().contains("IOException"));
        assertFalse(Files.exists(dir.resolve("pub.key")));
    }

    @Test
    void copyWithMissingInputReportsError() {
        new RunCopy().run(Arrays.asList(dir.resolve("missing").toString(), dir.resolve("out").toString()));

        assertTrue(errText().contains("Exception"));
    }

    @Test
    void genKeysDoesNotSupportCrypt() {
        RunGenKeys genKeys = new RunGenKeys();

        assertThrows(UnsupportedOperationException.class, () -> genKeys.crypt(new byte[0], null, null));
        assertEquals(100, genKeys.getCryptoBufSize());
    }
}
