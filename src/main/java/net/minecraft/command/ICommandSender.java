package net.minecraft.command;

import net.minecraft.utils.chat.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCoordinates;

public interface ICommandSender {
    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    String getCommandSenderName();

    void func_110122_a(ChatMessageComponent var1);

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    boolean canCommandSenderUseCommand(int var1, String var2);

    /**
     * Return the position for this command sender.
     */
    ChunkCoordinates getCommandSenderPosition();

    World getOverworld();
}
