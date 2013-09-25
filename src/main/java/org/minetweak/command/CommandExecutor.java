package org.minetweak.command;

import org.minetweak.Minetweak;
import org.minetweak.chat.TabCompletion;
import org.minetweak.chat.TextColor;
import org.minetweak.entity.Player;
import org.minetweak.util.StringUtils;
import org.minetweak.world.World;

import java.util.ArrayList;

public abstract class CommandExecutor implements ICommandExecutor {

    @Override
    public abstract void executeCommand(CommandSender sender, String overallCommand, String[] args);

    @Override
    public String getHelpInfo() {
        return "Not specified";
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

    /**
     * Get the overworld for the server.
     *
     * @return Server overworld
     */
    public World getOverworld() {
        return Minetweak.getOverworld();
    }

    /**
     * Get the Minecraft protocol version
     *
     * @see <a href="http://wiki.vg/Protocol">Protocol</a>
     * @return Protocol version
     */
    public int getProtocolVersion() {
        return Minetweak.getProtocolVersion();
    }

    /**
     * Kill a player.
     *
     * @param player Player to kill
     */
    public void killPlayer(Player player) {
        player.killPlayer();
    }

    /**
     * Kick player from the server.
     *
     * @param player Player to kick
     */
    public void kickPlayer(Player player) {
        player.kickPlayer();
    }

    /**
     * Kick player from the server with a reason.
     *
     * @param player Player to kick
     * @param reason Reason player was kicked
     */
    public void kickPlayer(Player player, String reason) {
        player.kickPlayer(reason);
    }

    /**
     * Ban player from the server.
     * @param player Player to ban
     */
    public void banPlayer(Player player) {
        player.banPlayer();
    }

    /**
     * Ban player from the server with a reason.
     * @param player Player to ban
     * @param reason Reason player was banned
     */
    public void banPlayer(Player player, String reason) {
        player.banPlayer(reason);
    }
}
