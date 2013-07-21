package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.WorldInfo;

public class CommandWeather extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length==0) {
            sender.sendMessage("Usage: /" + overallCommand + " rain/sun");
            return;
        }
        if (sender.hasPermission("minetweak.command.weather")) {
            WorldInfo info = MinecraftServer.getServer().worldServerForDimension(0).getWorldInfo();
            String id = args[1];
            if (id.equalsIgnoreCase("rain")) {
                info.setRaining(true);
            } else {
                sender.sendMessage("Invalid Weather Type.");
            }
        }
    }
}