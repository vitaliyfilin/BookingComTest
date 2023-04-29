package Infrastructure;

public class Awaiter {
    public static synchronized void waitOnObject(Object obj, long timeoutMillis) {
        try {
            obj.wait(timeoutMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
