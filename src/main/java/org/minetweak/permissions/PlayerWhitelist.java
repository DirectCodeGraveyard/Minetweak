package org.minetweak.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.minetweak.Minetweak;
import org.minetweak.config.GameConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages Players on the Whitelist
 */
public class PlayerWhitelist {
    private static Set<String> whitelistedPlayers = new HashSet<String>();
    private static File whitelistFile = new File("whitelist.json");
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void addPlayer(String name) {
        whitelistedPlayers.add(name.toLowerCase());
        save();
    }

    public static void removePlayer(String name) {
        whitelistedPlayers.remove(name.toLowerCase());
        save();
    }

    public static Set<String> getWhitelistedPlayers() {
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
                save();
                return;
            }
            FileReader reader = new FileReader(whitelistFile);
            Type dataType = new TypeToken<Set<String>>() {
            }.getType();
            whitelistedPlayers = gson.fromJson(reader, dataType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPlayerWhitelisted(String name) {
        return whitelistedPlayers.contains(name.toLowerCase());
    }

    public static boolean isWhitelistEnabled() {
        return GameConfig.getBoolean("server.whitelist-enabled", false);
    }

    public static void setWhitelistEnabled(boolean whitelistEnabled) {
        GameConfig.set("server.whitelist-enabled", String.valueOf(whitelistEnabled));
    }
}
