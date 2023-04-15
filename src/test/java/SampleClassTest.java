import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SampleClassTest {
    @Test
    void increaseTest() {
        SampleClass sc = new SampleClass(0);
        sc.increase();
        assertThat(sc.getCnt()).isEqualTo(1);
    }
}