package org.minetweak.command;

import org.minetweak.chat.TextColor;
import org.minetweak.util.StringUtils;

public class CommandWeather extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (args.length != 1 && args.length != 2) {
            sender.sendMessage("Usage: /" + overallCommand + " rain/clear/thunder [rain time]");
            return;
        } else if (args.length == 2 && !StringUtils.isInteger(args[1])) {
            sender.sendMessage("Rain time must be an integer");
        }

        if (sender.hasPermission("minetweak.command.weather")) {
            String id = args[0].toLowerCase();
            if (id.equalsIgnoreCase("rain") && args.length == 1) {
                getOverworld().setRaining(true);
            } else if (id.equalsIgnoreCase("rain") && args.length == 2) {
                int rainTime = Integer.parseInt(args[1]);
                getOverworld().setRaining(true);
                getOverworld().setRainTime(rainTime);
            } else if (id.equalsIgnoreCase("clear")) {
                getOverworld().setRaining(false);
                getOverworld().setThundering(false);
            } else if (id.equalsIgnoreCase("thunder")) {
                getOverworld().setThundering(true);
                getOverworld().setRaining(true);
            } else {
                sender.sendMessage(TextColor.RED + "Invalid Weather Type: " + id);
                return;
            }
            getOverworld().broadcastMessage("Weather changed to " + TextColor.GREEN + id);
        } else {
            noPermission(sender, "change the weather");
        }
    }

    @Override
    public String getHelpInfo() {
        return "Change the weather";
    }
}