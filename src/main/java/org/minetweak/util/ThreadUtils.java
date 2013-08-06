package org.minetweak.util;

public class ThreadUtils {
    public static void sleepSafe(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
