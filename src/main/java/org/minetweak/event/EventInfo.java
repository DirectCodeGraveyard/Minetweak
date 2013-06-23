package org.minetweak.event;

import org.minetweak.event.player.PlayerChatEvent;

import java.util.ArrayList;

public class EventInfo {
    public static ArrayList<Class<? extends Event>> eventTypes = getEvents();

    private static ArrayList<Class<? extends Event>> getEvents() {
        ArrayList<Class<? extends Event>> events = new ArrayList<Class<? extends Event>>();
        events.add(PlayerChatEvent.class);
        return events;
    }
}
