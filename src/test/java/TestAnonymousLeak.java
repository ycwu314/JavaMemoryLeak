import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by ycwu on 2017/7/7.
 */
@RunWith(JUnit4.class)
public class TestAnonymousLeak {

    /**
     * does not create anonymous class
     */
    @Test
    public void testLambda() {
        int a = 100;
        Runnable task = () -> System.out.println(a);
    }

    /**
     * create anonymous class
     */
    @Test
    public void testAnonymous() {
        List<Runnable> runnableList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int k = i;
            runnableList.add(new Runnable() {
                @Override
                public void run() {
                    System.out.println(k);
                }
            });
        }
    }


    @Test
    public void testAnonymousWithWeakReference() throws InterruptedException {
        WeakReference<Serializable> reference = new WeakReference<Serializable>(new Serializable() {

            {
                List<byte[]> array = new ArrayList<>();
                for (int i = 0; i < 50; i++) {
                    Thread.sleep(200L);
                    array.add(new byte[1024 * 1024]);
                }
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });

        int count = 15;
        while (count-- > 0) {
            System.out.println(count);
            Thread.sleep(500L);
        }

        // trigger gc in visual vm
        while (true) {
            Thread.sleep(1000L);
            System.out.println(reference.get() == null);
        }

    }
}
