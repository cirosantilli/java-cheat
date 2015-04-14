import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Main {
    public static void main(String[] args) throws Exception {
        final String pakage = "";
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        // Hello World
        mbs.registerMBean(
                new Hello(),
                new ObjectName(pakage + ":type=Hello"));

        Queue<String> queue = new ArrayBlockingQueue<String>(10);
        queue.add("Request-1");
        queue.add("Request-2");
        queue.add("Request-3");
        mbs.registerMBean(
                new QueueSampler(queue),
                new ObjectName(pakage + ":type=QueueSampler"));

        // Wait forever
        System.out.println("Waiting for incoming requests...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
