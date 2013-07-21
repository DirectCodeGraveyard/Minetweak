package org.minetweak.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;
import org.minetweak.Minetweak;

import java.io.*;

public class PermissionsLoader {
    private static File file = new File("./permissions.json");
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static String lastData = "";

    /**
     * Loads the permissions file
     */
    public static void load() {
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Unable to create permissions store file!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileReader reader = new FileReader(file);
            String data = IOUtils.toString(reader);
            if (data.equals(lastData)) {
                reader.close();
                return;
            } else {
                if (Minetweak.isServerDoneLoading()) Minetweak.info("Detected Permissions file change.");
                lastData = data;
            }

            PermissionsFile permissionsFile = null;

            try {
                permissionsFile = gson.fromJson(data, PermissionsFile.class);
            } catch (JsonSyntaxException exception) {
                Minetweak.getLogger().logWarning("There was a syntax error in your Permissions file.");
            }

            if (permissionsFile==null || permissionsFile.entries==null) {
                return;
            }
            reader.close();
            Permissions.getPermissions().clear();
            for (PermissionsFile.entry entry : permissionsFile.entries) {
                Permissions.addPermission(entry.player, entry.permission);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            if (!(file.delete() && file.createNewFile())) {
                Minetweak.getLogger().logSevere("Unable to create/delete permissions file at " + file.getAbsolutePath());
            }
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(Permissions.getPermissions()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
