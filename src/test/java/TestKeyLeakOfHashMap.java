import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.IntStream;

/**
 * Created by Administrator on 2017/7/6 0006.
 */
@RunWith(JUnit4.class)
public class TestKeyLeakOfHashMap {

    private static final int SIZE = 1000;

    @Test
    public void testKeyWithoutEqualsAndHashCode() {
        Map<ItemA, Integer> map = new HashMap<>();
        IntStream.range(0, SIZE).forEach(i -> {
            map.put(new ItemA(1), 222);
        });

        Assert.assertEquals(SIZE, map.size());
    }

    @AllArgsConstructor
    @Data
    static class ItemA {
        private int a;
    }

    ////////////////////////

    @Test
    public void testKeyWithEqualsAndHashCode() {
        Map<ItemB, Integer> map = new HashMap<>();
        IntStream.range(0, SIZE).forEach(i -> {
            map.put(new ItemB(1), 222);
        });
        Assert.assertEquals(1, map.size());
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @Data
    static class ItemB {
        private int b;
    }

    ////////////////////////

    /**
     * no used keys will be cleaned after gc
     *
     * @throws InterruptedException
     */
    @Test
    public void testWeakHashMap() throws InterruptedException {
        WeakHashMap<ItemA, Integer> map = new WeakHashMap<>();
        IntStream.range(0, SIZE).forEach(i -> {
            map.put(new ItemA(1), 333);
        });

        System.gc();
        Thread.sleep(2000L);

        Assert.assertEquals(0, map.size());

        // keep a strong reference outside the map
        ItemA a = new ItemA(1);
        map.put(a, 444);
        Assert.assertEquals(1, map.size());
    }

    ////////////////////////

    /**
     * although it is the same ItemB instance, but since key value is changed, it is TWO different key to Map !!!
     */
    @Test
    public void testMutableKeyLeak() {
        Map<ItemB, Integer> map = new HashMap<>();
        ItemB b = new ItemB(1);
        map.put(b, 11);
        b.setB(2);
        map.put(b, 22);
        Assert.assertEquals(2, map.size());
    }

}
