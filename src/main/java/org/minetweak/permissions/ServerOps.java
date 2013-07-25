package org.minetweak.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.minetweak.Minetweak;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ServerOps {
    private static Set<String> ops = new HashSet<String>();
    private static File opListFile = new File("ops.json");
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void addOp(String name) {
        ops.add(name);
        save();
    }

    public static void removeOp(String name) {
        ops.remove(name);
        save();
    }

    public static Set<String> getOps() {
        return ops;
    }

    public static void save() {
        try {
            if (!opListFile.exists()) {
                if (!opListFile.createNewFile()) {
                    Minetweak.getLogger().logWarning("Unable to save whitelist. Could not create whitelist.json");
                    return;
                }
            }
            FileWriter writer = new FileWriter(opListFile);
            writer.write(gson.toJson(ops));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            if (!opListFile.exists()) {
                save();
                return;
            }
            FileReader reader = new FileReader(opListFile);
            ops = gson.fromJson(reader, ops.getClass());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPlayerOp(String name) {
        return ops.contains(name);
    }
}