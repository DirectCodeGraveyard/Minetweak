package org.minetweak.event.player;

import org.minetweak.entity.Player;
import org.minetweak.event.helper.Cancellable;

public class PlayerChatEvent extends PlayerEvent implements Cancellable {

    private String message;

    private boolean cancelled;

    public PlayerChatEvent(Player player, String message) {
        super(player);
        this.message = message;
    }

    /**
     * Gets the message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    /**
     * Sets the Message Sent
     *
     * @param message message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
