package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.ChatFormatting;
import org.minetweak.util.StringUtils;

import java.util.Set;

public class CommandHelp extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        Set<String> commands = CmdHelper.getCommands();
        int start = 0;
        int numberOfCommands = commands.size();
        int page = 1;
        int numberOfPages = numberOfCommands / 5;
        if (args.length > 0 && StringUtils.isInteger(args[0])) {
            page = Integer.parseInt(args[0]);
            start = (5 * page) - 1;
            if (numberOfCommands < start) {
                sender.sendMessage(ChatFormatting.GOLD + "Page Number too large. There are " + ChatFormatting.RESET + numberOfPages + " pages.");
                return;
            }
        }
        String[] cmds = commands.toArray(new String[commands.size()]);
        sender.sendMessage("Help Page #" + page + " of " + numberOfPages);
        for (int i = start; i < commands.size() && (i - start) != 5; i++) {
            sender.sendMessage("/" + ChatFormatting.BLUE + cmds[i] + ChatFormatting.RESET + " - " + Minetweak.getCommandExecutors().get(cmds[i]).getHelpInfo());
        }
    }

    @Override
    public String getHelpInfo() {
        return "Used to get help for commands";
    }

}
