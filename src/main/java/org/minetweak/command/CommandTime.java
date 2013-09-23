package org.minetweak.command;

import org.minetweak.chat.TextColor;
import org.minetweak.server.Server;
import org.minetweak.util.StringUtils;

public class CommandTime extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.time")) {
            noPermission(sender, "change time");
            return;
        } else if (args.length != 2) {
            sender.sendMessage("Usage: /" + overallCommand + " set <time>");
            return;
        }


        String timeName = args[1];
        long time;
        if (StringUtils.isInteger(timeName)) {
            time = Long.parseLong(timeName);
        } else {
            if (timeName.equalsIgnoreCase("dawn")) {
                time = 0;
            } else if (timeName.equalsIgnoreCase("day")) {
                time = 6000;
            } else if (timeName.equalsIgnoreCase("dusk")) {
                time = 12000;
            } else if (timeName.equalsIgnoreCase("midnight")) {
                time = 18000;
            } else {
                sender.sendMessage(TextColor.RED + "Invalid Time: " + timeName);
                return;
            }
        }
        getOverworld().setWorldTime(time);
        Server.sendToOps("Overworld time set to " + TextColor.GREEN + timeName);
    }

    @Override
    public String getHelpInfo() {
        return "Set the overworld time";
    }
}
