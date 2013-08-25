package org.minetweak.util;

import java.util.Timer;
import java.util.TimerTask;

public class TaskScheduler {
    public static Timer timer = new Timer(true);

    public static void schedule(final Task task, final long delay) {
        timer.schedule(convertTask(task), delay);
    }

    public static TimerTask convertTask(final Task task) {
        return new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        };
    }
}
