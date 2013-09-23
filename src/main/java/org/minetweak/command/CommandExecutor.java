package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TabCompletion;
import org.minetweak.chat.TextColor;
import org.minetweak.util.StringUtils;
import org.minetweak.world.World;

import java.util.ArrayList;

public abstract class CommandExecutor implements ICommandExecutor {

    @Override
    public abstract void executeCommand(CommandSender sender, String overallCommand, String[] args);

    @Override
    public String getHelpInfo() {
        return "Not Specified";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        completions.addAll(TabCompletion.getPlayersMatching(input));
    }

    /**
     * Merges Arguments of a command into a String
     *
     * @param args the arguments of the command
     * @return merged string
     */
    public static String mergeArgs(String[] args) {
        return StringUtils.toString(args);
    }

    /**
     * Tells the Sender that they do not have permission to do something.
     *
     * @param sender the Command Sender
     */
    public void noPermission(CommandSender sender) {
        sender.sendMessage(TextColor.RED + "You do not have permission to do that.");
    }

    /**
     * Tells the sender they do not have permission to do the specified action
     *
     * @param sender the Command Sender
     * @param action the Action to be Performed
     */
    public void noPermission(CommandSender sender, String action) {
        sender.sendMessage(TextColor.RED + "You do not have permission to " + action + ".");
    }

    /**
     * Tells the Sender that this command is not for Consoles
     *
     * @param sender the Command Sender
     */
    public void notPlayer(CommandSender sender) {
        sender.sendMessage(TextColor.RED + "This command cannot be used by consoles.");
    }

    public World getOverworld() {
        return Minetweak.getOverworld();
    }

    public int getProtocolVersion() {
        return Minetweak.getProtocolVersion();
    }
}
