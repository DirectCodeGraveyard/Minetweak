package org.minetweak.command;

import net.minecraft.server.MinecraftServer;

public class CommandMotd extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        String motd = mergeArgs(args);
        if (motd.equals("")) {
            sender.sendMessage(MinecraftServer.getServer().getMOTD());
            return;
        }
        if (sender.hasPermission("minetweak.command.motd")) {
            MinecraftServer.getServer().setMOTD(motd);
            sender.sendMessage("MOTD Set.");
        } else {
            noPermission(sender, "set the MOTD");
        }
    }

    @Override
    public String getHelpInfo() {
        return "Gets/Sets the MOTD";
    }
}