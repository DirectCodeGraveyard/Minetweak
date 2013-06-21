package org.minetweak;

import net.minecraft.server.MinecraftServer;
import org.minetweak.entity.Player;

import java.util.ArrayList;

public class Minetweak {

    //TODO: Write JavaDocs for every public method

    private static final String minecraftVersion = "1.5.2";
    private static final String serverVersion = "0.0.1";

    private static boolean isServerDoneLoading = false;
    private static boolean hadRamWarning = false;
    private static boolean lockdownEnabled = false;

    private static ArrayList<String> playerUsernameList = new ArrayList<String>();
    private static ArrayList<Player> playerArrayList = new ArrayList<Player>();

    public static void main(String[] args) {
        System.out.println("Success is very tasty.");
        System.out.println("Minetweak v" + getServerVersion() + " using Minecraft v" + getMinecraftVersion());

        ramCheck();

        MinecraftServer.main(args);
    }

    private static void ramCheck() {
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            System.out.println("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
            hadRamWarning = true;
        } else {
            System.out.println("Good, you are using plenty of RAM to run Minetweak, I believe it is thanking you already!");
            hadRamWarning = false;
        }
    }

    /**
     * What Minecraft version are we running?
     * @return Minecraft Version
     */
    public static String getMinecraftVersion() {
        return minecraftVersion;
    }

    /**
     * What Minetweak server version are we running?
     * @return Minetweak Server Version
     */
    public static String getServerVersion() {
        return serverVersion;
    }

    /**
     * Is the server done loading?
     * @return
     */
    public static boolean isServerDoneLoading() {
        return isServerDoneLoading;
    }

    /**
     * Tell Minetweak that the Minecraft server itself has finished loading
     */
    public static void setServerDoneLoading() {
        isServerDoneLoading = true;
    }

    /**
     * Return true if the server had a RAM warning showing that we need to use more of it
     * @return If the server had a RAM warning
     */
    public static boolean hadRamWarning() {
        return hadRamWarning;
    }

    /**
     * Register a player into Minetweak
     * @param playerUsername Player name we are registering
     */
    public static boolean registerPlayer(String playerUsername) {
        Player targetPlayerInstance = new Player(playerUsername);
        for (String s : playerUsernameList) {
            if (s == playerUsername) {
                System.out.println(playerUsername + " was already registered!");
                // TODO: Add a kick message of Internal server error or something
                targetPlayerInstance.kickPlayer();
                return false;
            }
        }
        playerArrayList.add(targetPlayerInstance);
        playerUsernameList.add(playerUsername);
        System.out.println(playerUsername + " has been registered successfully!");
        if (lockdownEnabled) {
            targetPlayerInstance.kickPlayer();
            return false;
        }
        return true;
    }

    /**
     * Unregister a player out of Minetweak
     * @param playerUsername Player name we are unregistering
     */
    public static void unregisterPlayer(String playerUsername) {}
}
