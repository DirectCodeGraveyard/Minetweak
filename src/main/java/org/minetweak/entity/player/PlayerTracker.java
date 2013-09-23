package org.minetweak.entity.player;

import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.minetweak.Minetweak;
import org.minetweak.entity.Player;
import org.minetweak.event.player.NewPlayerEvent;
import org.minetweak.event.player.PlayerJoinEvent;
import org.minetweak.event.server.ServerInitializedEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of players who have joined before
 */
public class PlayerTracker {
    private static PlayerTracker instance = new PlayerTracker();
    private File file = new File("./players.json");
    private Map<String, PlayerInfo> players = new HashMap<String, PlayerInfo>();
    private Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public static PlayerTracker getInstance() {
        return instance;
    }

    @Subscribe
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!players.containsKey(player.getName())) {
            // This player is new
            Minetweak.info("New Player: " + event.getPlayer().getName());
            players.put(player.getName(), new PlayerInfo(player.getName()));
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
                Minetweak.getLogger().logWarning("Unable to save list of joined players. Cannot delete file.");
                return;
            }
        }
        try {
            if (!file.createNewFile()) {
                Minetweak.getLogger().logWarning("Unable to save list of joined players. Cannot create file.");
                return;
            }
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(players));
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
            Map<String, PlayerInfo> playerList = gson.fromJson(reader, new TypeToken<Map<String, PlayerInfo>>(){}.getType());
            if (playerList == null) {
                reader.close();
                return;
            }
            players = playerList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
