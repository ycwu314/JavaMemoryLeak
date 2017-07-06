import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Administrator on 2017/7/6 0006.
 */
@RunWith(JUnit4.class)
public class TestAutoboxingLeak {

    /**
     * in visualvm, a lot of Long instances
     */
    @Test
    public void testAutoboxingWithoutGC() throws InterruptedException {
        Long sum = 0L;
        for (int i = 0; i < 500000; i++) {
            sum += i;
        }

        while (true) {
            Thread.sleep(1000L);
        }
    }

    /**
     * can be gc easily
     */
    @Test
    public void testAutoboxingWithGC() throws InterruptedException {
        Long sum = 0L;
        for (int i = 0; i < 500000; i++) {
            sum += i;
        }

        while (true) {
            System.gc();
            Thread.sleep(1000L);
        }
    }


    /**
     * primitive type doe
     */
    @Test
    public void testPrimitive() throws InterruptedException {
        long sum = 0L;
        for (int i = 0; i < 500000; i++) {
            sum += i;
        }

        while (true) {
            Thread.sleep(1000L);
        }
    }
}
