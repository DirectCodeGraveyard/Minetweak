package org.minetweak.util;

import com.google.common.eventbus.Subscribe;
import org.minetweak.Minetweak;

public class WaitForEvent<E> {
    private E event = null;

    public E waitFor() {
        Minetweak.getEventBus().register(this);
        while (event == null) ;
        Minetweak.getEventBus().unregister(this);
        return event;
    }

    @Subscribe
    public void onEvent(E event) {
        this.event = event;
    }
}
