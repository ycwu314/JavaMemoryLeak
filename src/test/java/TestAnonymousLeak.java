import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

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

}
