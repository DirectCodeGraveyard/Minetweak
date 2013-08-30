package org.minetweak.util;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Scheduler for any time based Tasks
 */
public class TaskScheduler {
    public static Timer timer = new Timer(true);

    /**
     * Schedule a Task to run after the delay
     *
     * @param task  task
     * @param delay delay
     */
    public static void schedule(final Task task, final long delay) {
        timer.schedule(convertTask(task, false), delay);
    }

    /**
     * Schedule a Task to run after the delay
     *
     * @param task      the task to run
     * @param delay     the delay to use
     * @param useThread specifies whether to use a thread to execute the task
     */
    public static void schedule(final Task task, final long delay, boolean useThread) {
        timer.schedule(convertTask(task, useThread), delay);
    }

    /**
     * Schedule a Task to run at the Specified Time
     *
     * @param task the task to run
     * @param date time to run at
     */
    public static void scheduleAt(final Task task, final Date date) {
        timer.schedule(convertTask(task, false), date);
    }

    /**
     * Schedule a Task to run at the Specified Time
     *
     * @param task      the task to run
     * @param date      time to run at
     * @param useThread specifies whether to use a thread to execute the task
     */
    public static void scheduleAt(final Task task, final Date date, boolean useThread) {
        timer.schedule(convertTask(task, useThread), date);
    }

    /**
     * Converts the Given Task to a TimerTask
     *
     * @param task      the task to run
     * @param useThread specifies whether to use a thread to execute the task
     */
    public static TimerTask convertTask(final Task task, final boolean useThread) {
        return new TimerTask() {
            @Override
            public void run() {
                if (useThread) {
                    task.runInThread();
                } else {
                    task.run();
                }
            }
        };
    }

    /**
     * The Main Task Scheduling Timer
     *
     * @return timer
     */
    public static Timer getTimer() {
        return timer;
    }
}