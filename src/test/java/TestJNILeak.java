import java.lang.reflect.Field;
import org.junit.Test;
import sun.misc.Unsafe;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
public class TestJNILeak {

    @Test
    public void tesUnsafe()
        throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InterruptedException {
        Class clazz = Class.forName("sun.misc.Unsafe");
        Field f = clazz.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        for (int i = 0; i < 70; i++) {
            unsafe.allocateMemory(1024 * 1024);
            Thread.sleep(100L);
        }
    }
}
