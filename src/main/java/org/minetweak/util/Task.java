package org.minetweak.util;

public abstract class Task implements Runnable {

    public abstract void run();

    public Thread runInThread() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }
}
