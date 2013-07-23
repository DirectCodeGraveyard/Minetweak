package org.minetweak.command;

import net.minecraft.utils.enums.EnumChatFormatting;
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
        return "Not Specified";
    }

    /**
     * Merges Arguments of a command into a String
     * @param args the arguments of the command
     * @return merged string
     */
    public static String mergeArgs(String[] args) {
        return StringUtils.toString(args);
    }

    /**
     * Tells the Sender that they do not have permission to do something.
     * @param sender the Command Sender
     */
    public void noPermission(CommandSender sender) {
        sender.sendMessage(EnumChatFormatting.RED + "You do not have permission to do that.");
    }

    /**
     * Tells the sender they do not have permission to do the specified action
     * @param sender the Command Sender
     * @param action the Action to be Performed
     */
    public void noPermission(CommandSender sender, String action) {
        sender.sendMessage(EnumChatFormatting.RED + "You do not have permission to " + action + ".");
    }
}
