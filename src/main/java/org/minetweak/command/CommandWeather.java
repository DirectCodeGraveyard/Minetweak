package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TextColor;

public class CommandWeather extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /" + overallCommand + " rain/clear/thunder [rain: time]");
            return;
        }
        if (sender.hasPermission("minetweak.command.weather")) {
            String id = args[0];
            if (id.equalsIgnoreCase("rain")) {
                Minetweak.getOverworld().setRaining(true);
            } else if (id.equalsIgnoreCase("clear")) {
                Minetweak.getOverworld().setRaining(false);
                Minetweak.getOverworld().setThundering(false);
                sender.sendMessage("Done.");
            } else if (id.equals("thunder")) {
                Minetweak.getOverworld().setThundering(true);
                Minetweak.getOverworld().setRaining(true);
                sender.sendMessage("Done.");
            } else {
                sender.sendMessage(TextColor.RED + "Invalid Weather Type: " + id);
                return;
            }
            Minetweak.getOverworld().broadcastMessage("Weather changed to " + TextColor.GREEN + id);
        } else {
            noPermission(sender, "change the weather");
        }
    }

    @Override
    public String getHelpInfo() {
        return "Changes Weather in thw world.";
    }
}