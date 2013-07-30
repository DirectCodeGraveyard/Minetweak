package org.minetweak.command;

import net.minecraft.server.MinecraftServer;

public class CommandDebug extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.debug")) {
            noPermission(sender, "debug the server");
            return;
        }
        sender.sendMessage("TPS:" + " " + CommandTps.getTPS(MinecraftServer.getServer().tickTimeArray) + "\n" + "Total Memory:" + " " + Runtime.getRuntime().totalMemory() + "\n" + "Free Memory:" + " " + Runtime.getRuntime().freeMemory() + "\n" + "Max Memory:" + " " + Runtime.getRuntime().maxMemory());
    }
}
