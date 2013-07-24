package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.utils.enums.EnumChatFormatting;
import net.minecraft.world.WorldInfo;

public class CommandWeather extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length==0) {
            sender.sendMessage("Usage: /" + overallCommand + " rain/clear/thunder [rain: time]");
            return;
        }
        if (sender.hasPermission("minetweak.command.weather")) {
            WorldInfo info = MinecraftServer.getServer().worldServerForDimension(0).getWorldInfo();
            String id = args[0];
            if (id.equalsIgnoreCase("rain")) {
                info.setRaining(true);
                sender.sendMessage("Done.");
            } else if (id.equalsIgnoreCase("clear")) {
                info.setRaining(false);
                info.setThundering(false);
                sender.sendMessage("Done.");
            } else if (id.equals("thunder")) {
                info.setThundering(true);
                sender.sendMessage("Done.");
            } else {
                sender.sendMessage(EnumChatFormatting.RED + "Invalid Weather Type: " + id);
            }
        } else {
            noPermission(sender, "change the weather");
        }
    }

    @Override
    public String getHelpInfo() {
        return "Changes Weather";
    }
}