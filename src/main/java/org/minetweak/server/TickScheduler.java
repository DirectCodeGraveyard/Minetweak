package org.minetweak.server;

import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;

public class TickScheduler {
    private static ArrayList<TickTask> queue = new ArrayList<TickTask>();

    /**
     * Runs every Server Tick
     */
    public static void tick() {
        if (!queue.isEmpty()) {
            for (TickTask task : queue) {
                if (MinecraftServer.getServer().getTickCounter() % task.getTickInterval() == 0) {
                    task.run();
                }
            }
        }
    }

    /**
     * Schedules a Tick Task
     *
     * @param task Tick Task
     */
    public static void schedule(TickTask task) {
        queue.add(task);
    }
}
