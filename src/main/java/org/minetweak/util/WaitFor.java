package org.minetweak.util;

import com.google.common.eventbus.Subscribe;
import org.minetweak.Minetweak;

/**
 * A Utility to wait for an event
 * NOTE: This is a Work in Progress
 */
public class WaitFor<E> {
    private E event = null;
    private Task task;
    private boolean isRegistered = false;

    public E waitFor() {
        if (!isRegistered) {
            Minetweak.getEventBus().register(this);
        }
        isRegistered = true;
        while (event == null) ;
        if (isRegistered) {
            Minetweak.getEventBus().unregister(this);
        }
        isRegistered = false;
        E theEvent = event;
        event = null;
        return theEvent;
    }

    public void executeOnEvent(Task task) {
        if (!isRegistered) {
            Minetweak.getEventBus().register(this);
        }
        this.task = task;
    }

    @Subscribe
    public void onEvent(E event) {
        this.event = event;

        if (task != null) {
            task.run();
        }
    }
}
