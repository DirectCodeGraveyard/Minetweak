package org.minetweak.command;

public interface CommandSender {

    /**
     * Sends a Message to the Sender
     *
     * @param message the message to send
     */
    public void sendMessage(String message);

    /**
     * Sends multiple messages - Useful in multi-line situations
     *
     * @param messages Array of Messages to send
     */
    public void sendMessage(String[] messages);

    /**
     * Gets the Sender's name
     *
     * @return sender's name
     */
    public String getName();

    /**
     * Checks if the Sender is Kickable
     *
     * @return if the sender is kickable
     */
    public boolean isKickable();

    /**
     * Checks if the Sender is a Player
     *
     * @return if the sender is a player
     */
    public boolean isPlayer();

    /**
     * Checks if the Sender has a Permission
     *
     * @param permission the permission node
     * @return if the sender has the permission
     */
    public boolean hasPermission(String permission);
}
