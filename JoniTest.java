import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class JoniTest {
    @Test
    @DisplayName("Simple smoke test")
    void smoke() {
        System.out.println("In test: before");
        assertFalse(true);
        System.out.println("In test: after");
    }
}