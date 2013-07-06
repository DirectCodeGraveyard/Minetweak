package org.minetweak.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class PermissionsLoader {
    private static File file = new File("./permissions.json");
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
            PermissionsFile permissionsFile = gson.fromJson(reader, PermissionsFile.class);
            if (permissionsFile==null || permissionsFile.entries==null) {
                return;
            }
            Permissions.permissions.clear();
            for (PermissionsFile.entry entry : permissionsFile.entries) {
                Permissions.addPermission(entry.player, entry.permission);
            }
        } catch (FileNotFoundException e) {
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
