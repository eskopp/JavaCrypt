package javacrypt;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CopyTest {

    @TempDir
    Path dir;

    @Test
    void copiesFileLargerThanOneBuffer() throws Exception {
        byte[] data = new byte[RunCopy.RSA_COPY_BUFSIZE * 2 + 17];
        new Random(1).nextBytes(data);
        Path in = dir.resolve("in.dat");
        Path out = dir.resolve("out.dat");
        Files.write(in, data);

        new RunCopy().run(Arrays.asList(in.toString(), out.toString()));

        assertArrayEquals(data, Files.readAllBytes(out));
    }
}
