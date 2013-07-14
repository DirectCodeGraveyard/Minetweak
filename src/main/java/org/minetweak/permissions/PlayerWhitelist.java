package org.minetweak.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.minetweak.Minetweak;
import org.minetweak.Server;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerWhitelist {
    private static ArrayList<String> whitelistedPlayers = new ArrayList<String>();
    private static File whitelistFile = new File("whitelist.json");
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void addPlayer(String name) {
        whitelistedPlayers.add(name);
        save();
    }

    public static void removePlayer(String name) {
        whitelistedPlayers.remove(name);
        save();
    }

    public static ArrayList<String> getWhitelistedPlayers() {
        return whitelistedPlayers;
    }

    public static void save() {
        try {
            if (!whitelistFile.exists()) {
                if (!whitelistFile.createNewFile()) {
                    Minetweak.getLogger().logWarning("Unable to save whitelist. Could not create whitelist.json");
                    return;
                }
            }
            FileWriter writer = new FileWriter(whitelistFile);
            writer.write(gson.toJson(whitelistedPlayers));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            if (!whitelistFile.exists()) {
                return;
            }
            FileReader reader = new FileReader(whitelistFile);
            whitelistedPlayers = gson.fromJson(reader, whitelistedPlayers.getClass());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPlayerWhitelisted(String name) {
        return !Server.isWhitelistEnabled() || whitelistedPlayers.contains(name);
    }
}
