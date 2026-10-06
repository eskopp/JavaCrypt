package javacrypt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FactoryTest {

    private final RunnableKeyValueFactory factory = new RunnableKeyValueFactory(new String[][]{
        {"-encrypt", "javacrypt.RunEncrypt"},
        {"-copy", "javacrypt.RunCopy"}
    });

    @Test
    void knowsRegisteredKeywords() {
        assertTrue(factory.containsKey("-encrypt"));
        assertFalse(factory.containsKey("-unknown"));
        assertFalse(factory.containsKey(null));
    }

    @Test
    void resolvesClassName() {
        assertEquals("javacrypt.RunCopy", factory.getClassNameOfKey("-copy"));
        assertNull(factory.getClassNameOfKey("-unknown"));
    }

    @Test
    void instantiatesRunnable() throws Exception {
        assertInstanceOf(RunEncrypt.class, factory.getInstanceFromKey("-encrypt"));
        assertNull(factory.getInstanceFromKey("-unknown"));
    }

    @Test
    void acceptsNullMapping() {
        assertFalse(new RunnableKeyValueFactory(null).containsKey("-copy"));
    }
}
