package org.minetweak.event;

import java.util.HashMap;

public class Listeners {
    private static HashMap<String, Listener> listeners = new HashMap<String, Listener>();
    private static ThreadedEventExecutor executor = new ThreadedEventExecutor();

    public static void registerListener(String id, Listener listener) {
        listeners.put(id, listener);
    }

    public static void removeListener(String id) {
        listeners.remove(id);
    }

    public static void execute(Event event) {
        for (Listener listener : listeners.values()) {
            executor.execute(listener, event);
        }
    }
}
