package org.minetweak.command;

import net.minecraft.server.MinecraftServer;

public class CommandDebug extends CommandExecutor {

    private static int mb = 1024 * 1024;

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.debug")) {
            noPermission(sender, "debug");
            return;
        }

        sender.sendMessage("TPS: " + CommandTps.getTPS(MinecraftServer.getServer().tickTimeArray));
        sender.sendMessage("Total Memory: " + Runtime.getRuntime().totalMemory() / mb + "mb");
        sender.sendMessage("Free Memory: " + Runtime.getRuntime().freeMemory() / mb + "mb");
        sender.sendMessage("Maximum Memory: " + Runtime.getRuntime().maxMemory() / mb + "mb");
    }
}
