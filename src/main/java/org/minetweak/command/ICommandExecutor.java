package org.minetweak.command;

import java.util.ArrayList;

public interface ICommandExecutor {

    /**
     * Executes the PluginCommand
     *
     * @param sender         Sender of the PluginCommand
     * @param overallCommand PluginCommand that it was executed with
     * @param args           arguments as an array
     */
    public void executeCommand(CommandSender sender, String overallCommand, String[] args);

    /**
     * Gets the message to be displayed on the help command
     *
     * @return help text
     */
    public String getHelpInfo();

    /**
     * Gets a list of tab completion Options
     *
     * @param sender      Sender
     * @param line        Input from Chat Box
     * @param completions list of completions
     */
    public void getTabCompletion(CommandSender sender, String line, ArrayList<String> completions);
}
