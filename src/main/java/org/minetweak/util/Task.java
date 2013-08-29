package org.minetweak.util;

import groovy.lang.Closure;

public abstract class Task implements Runnable {

    public static Task newTask(final Runnable runner) {
        return new Task() {
            @Override
            public void run() {
                runner.run();
            }
        };
    }

    public static Task newTask(final Closure closure) {
        return new Task() {
            @Override
            public void run() {
                closure.call();
            }
        };
    }

    public Thread runInThread() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }
}
