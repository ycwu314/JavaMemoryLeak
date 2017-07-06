import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.stream.IntStream;

/**
 * Created by ycwu on 2017/7/6.
 */
@RunWith(JUnit4.class)
public class TestArrayShrinkSizeLeak {

    class LeakableArray {
        private int maxSize;
        private int index;
        private Object[] array;

        public LeakableArray(int maxSize) {
            this.maxSize = maxSize;
            index = -1;
            array = new Object[maxSize];
        }

        public boolean add(Object obj) {
            if (index + 1 < maxSize) {
                array[++index] = obj;
                return true;
            } else {
                return false;
            }
        }

        public Object remove() {
            if (index >= 0) {
                return array[index--];
            } else {
                return null;
            }
        }

        public Object[] getArray() {
            return array;
        }
    }

    private static final int SIZE = 10;

    @Test
    public void test() {
        LeakableArray leakableArray = new LeakableArray(SIZE);
        IntStream.range(0, SIZE).forEach(i -> {
            leakableArray.add(new Object());
        });

        IntStream.range(0, SIZE).forEach(i -> {
            leakableArray.remove();
        });

        int count = 0;
        for (Object obj : leakableArray.getArray()) {
            if (obj != null) {
                count++;
            }
        }

        Assert.assertEquals(SIZE, count);
    }

    /////////////////////////////////


    class NotLeakableArray {
        private int maxSize;
        private int index;
        private Object[] array;

        public NotLeakableArray(int maxSize) {
            this.maxSize = maxSize;
            index = -1;
            array = new Object[maxSize];
        }

        public boolean add(Object obj) {
            if (index + 1 < maxSize) {
                array[++index] = obj;
                return true;
            } else {
                return false;
            }
        }

        /**
         * as the array shrinks, those removed index should point to null !
         *
         * @return
         */
        public Object remove() {
            if (index >= 0) {
                Object obj = array[index];
                array[index--] = null;
                return obj;
            } else {
                return null;
            }
        }

        public Object[] getArray() {
            return array;
        }
    }

    @Test
    public void test2() {
        NotLeakableArray notLeakableArray = new NotLeakableArray(SIZE);
        IntStream.range(0, SIZE).forEach(i -> {
            notLeakableArray.add(new Object());
        });

        IntStream.range(0, SIZE).forEach(i -> {
            notLeakableArray.remove();
        });

        int count = 0;
        for (Object obj : notLeakableArray.getArray()) {
            if (obj != null) {
                count++;
            }
        }

        Assert.assertEquals(0, count);
    }
}
