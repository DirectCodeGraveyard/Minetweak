package org.minetweak.util;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TaskScheduler {
    public static Timer timer = new Timer(true);

    public static void schedule(final Task task, final long delay) {
        timer.schedule(convertTask(task, false), delay);
    }

    public static void schedule(final Task task, final long delay, boolean useThread) {
        timer.schedule(convertTask(task, useThread), delay);
    }

    public static void scheduleAt(final Task task, final Date date) {
        timer.schedule(convertTask(task, false), date);
    }

    public static void scheduleAt(final Task task, final Date date, boolean useThread) {
        timer.schedule(convertTask(task, useThread), date);
    }

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

    public static Timer getTimer() {
        return timer;
    }
}