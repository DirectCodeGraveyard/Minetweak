package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TextColor;

public class CommandVersion extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 0) {
            sender.sendMessage("Usage: /version");
            return;
        }

        sender.sendMessage(TextColor.GREEN + "You are running " + "Minetweak" + " v" + Minetweak.getAPIVersion() + ", using Minecraft v" + Minetweak.getMinecraftVersion());
    }

    @Override
    public String getHelpInfo() {
        return "Gets the Server Version";
    }

}
