package org.minetweak.command;

import java.util.ArrayList;

public interface ICommandExecutor {
    public void executeCommand(CommandSender sender, String commandName, String[] args);

    public String getHelpInfo();

    public void getTabCompletion(CommandSender sender, String line, ArrayList<String> completions);
}
