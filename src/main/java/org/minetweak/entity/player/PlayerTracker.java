package org.minetweak.entity.player;

import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.MinecraftServer;
import org.minetweak.Minetweak;
import org.minetweak.event.player.NewPlayerEvent;
import org.minetweak.event.player.PlayerJoinEvent;
import org.minetweak.event.server.ServerInitializedEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Keeps track of players who have joined before
 */
public class PlayerTracker {
    private static PlayerTracker instance = new PlayerTracker();
    private File file = new File("./players.json");
    private ArrayList<String> players = new ArrayList<String>();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static PlayerTracker getInstance() {
        return instance;
    }

    @Subscribe
    public void onJoin(PlayerJoinEvent event) {
        if (!players.contains(event.getPlayer().getName())) {
            // This player is new
            Minetweak.info("New Player: " + event.getPlayer().getName());
            players.add(event.getPlayer().getName());
            Minetweak.getEventBus().post(new NewPlayerEvent(event.getPlayer()));
            saveList();
        }
    }

    @Subscribe
    public void onInit(ServerInitializedEvent event) {
        loadList();
    }

    public void saveList() {
        if (file.exists()) {
            if (!file.delete()) {
                MinecraftServer.getServer().getLogAgent().getLogger().log(Level.WARNING, "[Minetweak] Unable to save list of Joined Players. Cannot delete file.");
                return;
            }
        }
        try {
            if (!file.createNewFile()) {
                MinecraftServer.getServer().getLogAgent().getLogger().log(Level.WARNING, "[Minetweak] Unable to save list of Joined Players. Cannot create file.");
                return;
            }
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(new PlayerListFile(players)));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadList() {
        try {
            if (!file.exists()) {
                return;
            }
            FileReader reader = new FileReader(file);
            PlayerListFile playerList = gson.fromJson(reader, PlayerListFile.class);
            if (playerList == null || playerList.players == null) {
                reader.close();
                return;
            }
            players = playerList.players;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
