package org.minetweak.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.minetweak.Minetweak;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

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

            HashMap<String, ArrayList<String>> permissions = gson.fromJson(data, HashMap.class);
            if (permissions==null) {
                return;
            }
            Permissions.setPermissions(permissions);
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
