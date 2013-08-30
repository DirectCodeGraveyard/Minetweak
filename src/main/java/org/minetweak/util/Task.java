package org.minetweak.util;

import groovy.lang.Closure;

/**
 * Represents a Task to run on a Scheduler
 * Note: Task implements Runnable
 */
public abstract class Task implements Runnable {

    /**
     * Creates a new Task from a Runnable
     *
     * @param runner runnable
     * @return task
     */
    public static Task newTask(final Runnable runner) {
        return new Task() {
            @Override
            public void run() {
                runner.run();
            }
        };
    }

    /**
     * Creates a new Task from a Closure
     *
     * @param closure closure
     * @return task
     */
    public static Task newTask(final Closure closure) {
        return new Task() {
            @Override
            public void run() {
                closure.call();
            }
        };
    }

    /**
     * Runs this Task in a new Thread
     *
     * @return thread
     */
    public Thread runInThread() {
        Thread thread = new Thread(this);
        thread.start();
        return thread;
    }
}
