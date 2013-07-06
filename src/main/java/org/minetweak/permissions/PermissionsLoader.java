package org.minetweak.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.minetweak.Minetweak;

import java.io.*;

public class PermissionsLoader {
    private static File file = new File("./permissions.json");
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static String lastData = "";
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
                if (Minetweak.isServerDoneLoading()) {
                    Minetweak.info("Detected Permissions File Change. Loading....");
                }
                lastData = data;
            }
            PermissionsFile permissionsFile = gson.fromJson(data, PermissionsFile.class);
            if (permissionsFile==null || permissionsFile.entries==null) {
                return;
            }
            reader.close();
            Permissions.permissions.clear();
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
            file.delete();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(Permissions.permissions));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
