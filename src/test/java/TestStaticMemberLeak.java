import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
@RunWith(JUnit4.class)
public class TestStaticMemberLeak {


    private static final int SIZE = 1024 * 1024;

    @Test
    public void testLeak() throws InterruptedException {
        for (int i = 0; i < 60; i++) {
            ContentHolder.HOLDER.add(new byte[SIZE]);
            Thread.sleep(1000L);
        }

        // observe this in visual vm
        while (true) {
            System.gc();
            Thread.sleep(1000L);
        }
    }

    @Test
    public void testNotLeak() throws InterruptedException {
        ContentHolder2 holder2 = new ContentHolder2();
        for (int i = 0; i < 60; i++) {
            holder2.HOLDER.add(new byte[SIZE]);
            Thread.sleep(1000L);
        }

        holder2 = null;

        // observe this in visual vm
        while (true) {
            System.gc();
            Thread.sleep(1000L);
        }
    }

}


class ContentHolder {

    public static final List<byte[]> HOLDER = new ArrayList<>();
}


class ContentHolder2 {

    public final List<byte[]> HOLDER = new ArrayList<>();
}

