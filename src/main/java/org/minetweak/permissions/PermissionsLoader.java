package org.minetweak.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.minetweak.Minetweak;

import java.io.*;
import java.util.HashMap;

public class PermissionsLoader {
    private static File file = new File("./permissions.json");
    private static Gson gson = new GsonBuilder().create();
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
            for (PermissionsFile.entry entry : permissionsFile.entries) {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
