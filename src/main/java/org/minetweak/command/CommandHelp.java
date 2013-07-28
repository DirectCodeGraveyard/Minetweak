package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.ChatColors;
import org.minetweak.chat.TabCompletion;
import org.minetweak.util.StringUtils;

import java.util.ArrayList;
import java.util.Set;

public class CommandHelp extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        Set<String> commands = CmdHelper.getCommands();
        if (args.length > 0 && !StringUtils.isInteger(args[0])) {
            String command = args[0];
            if (commands.contains(command)) {
                String helpInfo = Minetweak.getCommandExecutors().get(command).getHelpInfo();
                sender.sendMessage("/" + ChatColors.GREEN + command + ChatColors.RESET + " - " + helpInfo);
                return;
            } else {
                sender.sendMessage("help: No Such Command: " + command);
            }
        }
        int start = 0;
        int numberOfCommands = commands.size();
        int page = 1;
        int numberOfPages = numberOfCommands / 5;
        if (args.length > 0 && StringUtils.isInteger(args[0])) {
            page = Integer.parseInt(args[0]);
            start = (5 * page) - 1;
            if (numberOfCommands < start) {
                sender.sendMessage(ChatColors.GOLD + "Page Number too large. There are " + ChatColors.RESET + numberOfPages + " pages.");
                return;
            }
        }
        String[] cmds = commands.toArray(new String[commands.size()]);
        sender.sendMessage("Help Page #" + page + " of " + numberOfPages);
        for (int i = start; i < commands.size() && (i - start) != 5; i++) {
            sender.sendMessage("/" + ChatColors.BLUE + cmds[i] + ChatColors.RESET + " - " + Minetweak.getCommandExecutors().get(cmds[i]).getHelpInfo());
        }
    }

    @Override
    public String getHelpInfo() {
        return "Used to get help for commands";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        String[] split = input.split(" ");
        int length = split.length;

        switch (length) {
            case 1:
                completions.addAll(Minetweak.getCommandExecutors().keySet());
                return;
            case 2:
                completions.addAll(TabCompletion.getCommandsMatching(split[1]));
        }
    }
}
