package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.Server;
import org.minetweak.chat.ChatFormatting;
import org.minetweak.util.StringUtils;

public class CommandTime extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.time")) {
            noPermission(sender, "change time");
            return;
        }
        if (args.length != 1) {
            sender.sendMessage("Usage: /" + overallCommand + " TIME");
            return;
        }
        String timeName = args[0];
        long time;
        if (StringUtils.isInteger(timeName)) {
            time = Long.parseLong(timeName);
        } else {
            if (timeName.equals("dawn")) {
                time = 0;
            } else if (timeName.equals("day")) {
                time = 6000;
            } else if (timeName.equals("dusk")) {
                time = 12000;
            } else if (timeName.equals("midnight")) {
                time = 18000;
            } else {
                sender.sendMessage(ChatFormatting.RED + "Invalid Time: " + timeName);
                return;
            }
        }
        Minetweak.getOverworld().setWorldTime(time);
        Server.sendToOps("Overworld time set to " + ChatFormatting.GREEN + timeName);
    }

    @Override
    public String getHelpInfo() {
        return "Sets the Overworld time";
    }
}
