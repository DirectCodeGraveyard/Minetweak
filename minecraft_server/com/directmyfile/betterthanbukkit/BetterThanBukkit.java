package com.directmyfile.betterthanbukkit;

import net.minecraft.server.MinecraftServer;

public class BetterThanBukkit {

    private static String minecraftVersion = "1.5.2";
    private static String serverVersion = "0.0.1";

    private static boolean isServerDoneLoading = false;
    private static boolean hadRamWarning;

    public static void main(String[] args) {
        System.out.println("Success is very tasty.");
        System.out.println("BetterThanMinecraft v" + getServerVersion() + " using Minecraft v" + getMinecraftVersion());

        ramCheck();

        MinecraftServer.main(args);
    }

    private static void ramCheck() {
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            System.out.println("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
            hadRamWarning = true;
        } else {
            System.out.println("Good, you are using plenty of RAM to run BetterThanBukkit, I believe it is thanking you already!");
            hadRamWarning = false;
        }
    }

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
        
    }
}
