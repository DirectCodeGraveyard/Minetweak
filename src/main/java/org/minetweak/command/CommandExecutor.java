package org.minetweak.command;

import org.minetweak.util.StringUtils;

public abstract class CommandExecutor implements ICommandExecutor {

    /**
     * Executes the PluginCommand
     * @param sender Sender of the PluginCommand
     * @param overallCommand PluginCommand that it was executed with
     * @param args arguments as an array
     */
    public abstract void executeCommand(CommandSender sender, String overallCommand, String[] args);

    /**
     * Gets the message to be displayed on the help command
     * @return help text
     */
    public String getHelpInfo() {
        return "";
    }

    /**
     * Merges Arguments of a command into a String
     * @param args the arguments of the command
     * @return merged string
     */
    public static String mergeArgs(String[] args) {
        return StringUtils.toString(args);
    }

}
