package org.minetweak.event.world;

import org.minetweak.event.helper.Cancellable;

public class SignUpdateEvent implements Cancellable {
    private boolean cancel = false;
    private String[] text;

    public SignUpdateEvent(String[] text) {
        this.text = text;
    }

    public String[] getSignText() {
        return text;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public void setText(String[] text) {
        this.text = text;
    }
}
