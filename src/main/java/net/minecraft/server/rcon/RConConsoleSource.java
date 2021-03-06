package net.minecraft.server.rcon;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.utils.chat.ChatMessageComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkCoordinates;
import org.minetweak.command.CommandSender;

import java.util.List;

public class RConConsoleSource implements ICommandSender, CommandSender {
    /**
     * Single instance of RConConsoleSource
     */
    public static final RConConsoleSource instance = new RConConsoleSource();

    /**
     * RCon string buffer for log.
     */
    private StringBuffer buffer = new StringBuffer();

    /**
     * Clears the RCon log
     */
    public void resetLog() {
        this.buffer.setLength(0);
    }

    /**
     * Gets the contents of the RCon log
     */
    public String getLogContents() {
        return this.buffer.toString();
    }

    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    @Override
    public String getCommandSenderName() {
        return "Rcon";
    }

    @Override
    public void func_110122_a(ChatMessageComponent par1ChatMessageComponent) {
        this.buffer.append(par1ChatMessageComponent.toString());
    }

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    @Override
    public boolean canCommandSenderUseCommand(int par1, String par2Str) {
        return true;
    }

    /**
     * Return the position for this command sender.
     */
    @Override
    public ChunkCoordinates getCommandSenderPosition() {
        return new ChunkCoordinates(0, 0, 0);
    }

    @Override
    public World getOverworld() {
        return MinecraftServer.getServer().getOverworld();
    }

    @Override
    public void sendMessage(String message) {
        this.buffer.append(message);
    }

    @Override
    public void sendMessage(String[] messages) {
        for (String message : messages) {
            this.sendMessage(message);
        }
    }

    @Override
    public void sendMessage(List<String> messages) {
        sendMessage(messages.toArray(new String[messages.size()]));
    }

    @Override
    public String getName() {
        return "RCon";
    }

    @Override
    public boolean isKickable() {
        return false;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        return true;
    }
}
