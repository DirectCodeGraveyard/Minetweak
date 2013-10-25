package org.minetweak.server;

import net.minecraft.entity.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.utils.chat.ChatMessageComponent;
import org.minetweak.Minetweak;
import org.minetweak.chat.TextColor;
import org.minetweak.config.GameConfig;
import org.minetweak.console.Console;
import org.minetweak.entity.Player;
import org.minetweak.permissions.ServerOps;
import org.minetweak.util.StringUtils;

/**
 * Used for methods that may not fit anywhere else, or
 * fit the "Server" name, those such as broadcastMessage
 * belong here, but those such as kickPlayer are here
 * for lazy developers who don't want to define a player
 * instance or store the player in a variable.
 */
public class Server {

    /**
     * Broadcast a message to the entire server
     *
     * @param message Message to broadcast
     * @return True on success for sent message(e.g. false on server not finished startup)
     */
    public static boolean broadcastMessage(String message) {
        if (Minetweak.isServerDoneLoading()) {
            MinecraftServer.getServer().getConfigurationManager().sendChatMessageToAll(ChatMessageComponent.createPremade(String.format("[%s%s%s] %s", TextColor.getConfigurableColor("broadcast.server.color", TextColor.DARK_PURPLE), "Server", TextColor.RESET, message)));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Kicks a player.
     *
     * @param playerName Player username to kick
     * @return True on kick success
     */
    public static boolean kickPlayer(String playerName) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if (targetPlayer == null) return false;
        targetPlayer.kickPlayer();
        return true;
    }

    /**
     * Kicks a player.
     *
     * @param playerName Player username to kick
     * @param kickReason Reason to kick
     * @return True on kick success
     */
    public static boolean kickPlayer(String playerName, String kickReason) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if (targetPlayer == null) return false;
        targetPlayer.kickPlayer(kickReason);
        return true;
    }

    /**
     * Bans a player.
     *
     * @param playerName Player username to ban
     * @return True on ban success
     */
    public static boolean banPlayer(String playerName) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if (targetPlayer == null) return false;
        targetPlayer.banPlayer();
        return true;
    }

    /**
     * Bans a player.
     *
     * @param playerName Player username to ban
     * @param banReason  Reason to ban the player
     * @return True on ban success
     */
    public static boolean banPlayer(String playerName, String banReason) {
        Player targetPlayer = Minetweak.getPlayerByName(playerName);
        if (targetPlayer == null) return false;
        targetPlayer.kickPlayer(banReason);
        return true;
    }

    /**
     * Initiate shutdown of the server
     */
    public static void shutdownServer() {
        MinecraftServer.getServer().initiateShutdown();
        MinecraftServer.getServer().stopServer();
    }

    /**
     * Called by Minecraft code to hook in our commands system.
     * WARNING: Should not be used in a plugin's point of view, it'll break stuff.
     *
     * @param player  The player instance it was sent by.
     * @param command Command sent by the player, in raw String form.
     */
    public static void handleCommand(EntityPlayerMP player, String command) {
        if (command.startsWith("/")) {
            command = command.substring(1);
        } else {
            return;
        }

        String[] commandWithArgs = command.split(" ");
        String commandOnly = commandWithArgs[0];
        String[] args = StringUtils.dropFirstString(commandWithArgs);

        if (Minetweak.doesCommandExist(commandWithArgs[0])) {
            Minetweak.getCommandByName(commandWithArgs[0]).executeCommand(Minetweak.getPlayerByName(player.getEntityName()), commandOnly, args);
        } else {
            player.addChatMessage(TextColor.getConfigurableColor("command.notfound.color", TextColor.RED) + "No Such Command: " + commandOnly);
        }
    }

    /**
     * Called by Minecraft code to hook in our commands system.
     * WARNING: Should not be used in a plugin's point of view, it will break stuff.
     *
     * @param console The console instance it was sent by.
     * @param command Command sent by the sender, in raw string form.
     */
    public static void handleCommand(Console console, String command) {
        if (command.startsWith("/")) {
            command = command.substring(1);
        }

        String[] commandWithArgs = command.split(" ");
        String commandOnly = commandWithArgs[0];
        String[] args = StringUtils.dropFirstString(commandWithArgs);

        if (Minetweak.doesCommandExist(commandWithArgs[0])) {
            Minetweak.getCommandByName(commandWithArgs[0]).executeCommand(console, commandOnly, args);
        } else {
            console.sendMessage(TextColor.RED + "No Such Command: " + commandOnly);
        }
    }

    /**
     * Op a player
     *
     * @param playerUsername Player username to op
     */
    public static void opPlayer(String playerUsername) {
        ServerOps.addOp(playerUsername);
    }

    /**
     * Deop a player
     *
     * @param playerUsername Player username to deop
     */
    public static void deopPlayer(String playerUsername) {
        ServerOps.removeOp(playerUsername);
    }

    /**
     * Pardon a player
     *
     * @param playerUsername Player to pardon
     */
    public static void pardonPlayer(String playerUsername) {
        MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().remove(playerUsername);
    }

    /**
     * Check if the whitelist is enabled.
     *
     * @return True if the whitelist is enabled
     */
    public static boolean isWhitelistEnabled() {
        return GameConfig.getBoolean("server.whitelist-enabled");
    }

    /**
     * Send a message to all operators on the server.
     *
     * @param message Message to send.
     */
    public static void sendToOps(String message) {
        String out = "[" + TextColor.DARK_PURPLE + "Server" + TextColor.RESET + "] " + message;

        if (ServerOps.getOps().size() == 0) return;

        for (String op : ServerOps.getOps()) {
            if (Minetweak.isPlayerOnline(op)) {
                Player player = Minetweak.getPlayerByName(op);
                player.sendMessage(out);
            }
        }

        for (CharSequence s : TextColor.getColorNodes()) {
            message = message.replace(s, "");
        }
        Minetweak.info(message);
    }
}
