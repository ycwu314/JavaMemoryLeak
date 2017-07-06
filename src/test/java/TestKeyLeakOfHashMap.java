import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Administrator on 2017/7/6 0006.
 */
@RunWith(JUnit4.class)
public class TestKeyLeakOfHashMap {

    @Test
    public void testKeyWithoutEqualsAndHashCode() {
        int size = 1000;
        Map<ItemA, Integer> map = new HashMap<>();
        IntStream.range(0, size).forEach(i -> {
            map.put(new ItemA(1), 222);
        });
        Assert.assertEquals(size, map.size());
    }

    @AllArgsConstructor
    static class ItemA {
        private int a;
    }

    ////////////////////////

    @Test
    public void testKeyWithEqualsAndHashCode() {
        int size = 1000;
        Map<ItemB, Integer> map = new HashMap<>();
        IntStream.range(0, size).forEach(i -> {
            map.put(new ItemB(1), 222);
        });
        Assert.assertEquals(1, map.size());
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    static class ItemB {
        private int a;
    }


}
