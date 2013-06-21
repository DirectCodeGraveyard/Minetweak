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
     *
     * @return
     */
    public static String getMinecraftVersion() {
        return minecraftVersion;
    }

    public static String getServerVersion() {
        return serverVersion;
    }

    public static boolean isServerDoneLoading() {
        return isServerDoneLoading;
    }

    public static void setServerDoneLoading() {
        isServerDoneLoading = true;
    }

    public static boolean hadRamWarning() {
        return hadRamWarning;
    }

    public static void registerPlayer(String playerUsername) {
        Player targetPlayerInstance = new Player(playerUsername);
        for (String s : playerUsernameList) {
            if (s == playerUsername) {
                System.out.println(playerUsername + " was already registered!");
                // TODO: Add a kick message of Internal server error or something
                targetPlayerInstance.kickPlayer();
                return;
            }
        }
        playerArrayList.add(targetPlayerInstance);
        playerUsernameList.add(playerUsername);
        System.out.println(playerUsername + " has been registered successfully!");
        if (lockdownEnabled) {
            targetPlayerInstance.kickPlayer();
        }
    }

    public static void unregisterPlayer(String playerUsername) {
        //Player
    }
}
