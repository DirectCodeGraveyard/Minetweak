package org.minetweak.command;

import net.minecraft.src.EnumChatFormatting;
import org.minetweak.Server;

public class CommandStop extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        Server.broadcastMessage(EnumChatFormatting.AQUA + "Stopping the server...");
        Server.shutdownServer();
    }

}
