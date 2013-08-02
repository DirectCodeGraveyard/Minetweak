package org.minetweak.permissions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.minetweak.Minetweak;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages Server Ops
 */
public class ServerOps {
    private static Set<String> ops = new HashSet<String>();
    private static File opListFile = new File("ops.json");
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void addOp(String name) {
        ops.add(name.toLowerCase());
        save();
    }

    public static void removeOp(String name) {
        ops.remove(name.toLowerCase());
        save();
    }

    public static Set<String> getOps() {
        return ops;
    }

    public static void save() {
        try {
            if (!opListFile.exists()) {
                if (!opListFile.createNewFile()) {
                    Minetweak.getLogger().logWarning("Unable to save whitelist. Could not create ops.json");
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
            Type dataType = new TypeToken<Set<String>>() {
            }.getType();
            ops = gson.fromJson(reader, dataType);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isPlayerOp(String name) {
        return ops.contains(name);
    }
}