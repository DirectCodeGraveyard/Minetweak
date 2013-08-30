package org.minetweak.util;

/**
 * Utilities for Threads
 */
public class ThreadUtils {
    /**
     * Provides an Exception-less Sleep
     *
     * @param time time in milliseconds to sleep
     */
    public static void sleepSafe(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
