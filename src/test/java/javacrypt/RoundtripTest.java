package javacrypt;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class RoundtripTest {

    @TempDir
    Path dir;

    private Path priv;
    private Path pub;

    @BeforeEach
    void generateKeys() {
        priv = dir.resolve("priv.key");
        pub = dir.resolve("pub.key");
        new RunGenKeys().run(Arrays.asList(priv.toString(), pub.toString()));
    }

    private byte[] roundtrip(byte[] plain) throws Exception {
        Path in = dir.resolve("in.dat");
        Path enc = dir.resolve("enc.dat");
        Path dec = dir.resolve("dec.dat");
        Files.write(in, plain);

        new RunEncrypt().run(Arrays.asList(pub.toString(), in.toString(), enc.toString()));
        new RunDecrypt().run(Arrays.asList(priv.toString(), enc.toString(), dec.toString()));

        return Files.readAllBytes(dec);
    }

    @Test
    void genKeysWritesBothKeyFiles() throws Exception {
        assertTrue(Files.size(priv) > 0);
        assertTrue(Files.size(pub) > 0);
    }

    @Test
    void shortTextSurvivesRoundtrip() throws Exception {
        byte[] plain = "Dear readers, today's weather is wet.".getBytes(StandardCharsets.UTF_8);

        assertArrayEquals(plain, roundtrip(plain));
    }

    @Test
    void ciphertextDiffersFromPlaintext() throws Exception {
        Path in = dir.resolve("in.dat");
        Path enc = dir.resolve("enc.dat");
        byte[] plain = "secret".getBytes(StandardCharsets.UTF_8);
        Files.write(in, plain);

        new RunEncrypt().run(Arrays.asList(pub.toString(), in.toString(), enc.toString()));

        byte[] cipherText = Files.readAllBytes(enc);
        assertEquals(RunnableBase.KEY_LENGTH / 8, cipherText.length);
        assertTrue(!Arrays.equals(plain, cipherText));
    }

    @Test
    void largeFileSurvivesRoundtrip() throws Exception {
        byte[] plain = new byte[1000];
        new Random(42).nextBytes(plain);

        assertArrayEquals(plain, roundtrip(plain));
    }
}
