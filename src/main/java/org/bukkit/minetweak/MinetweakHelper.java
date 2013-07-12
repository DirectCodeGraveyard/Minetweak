package org.bukkit.minetweak;

import org.bukkit.command.PluginCommand;
import org.minetweak.Minetweak;
import org.minetweak.plugins.PluginInfo;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class MinetweakHelper {
    public static HashMap<String, PluginCommand> commands = new HashMap<String, PluginCommand>();
    public static PluginInfo parsePluginYAML(JarFile jar) {
        ZipEntry entry = jar.getEntry("plugin.yaml");
        if (entry==null) {
            return null;
        }
        Yaml yaml = new Yaml();
        try {
            Map pluginYAML = (Map) yaml.load(jar.getInputStream(entry));
            String name = (String) pluginYAML.get("name");
            String mainClass = (String) pluginYAML.get("main");
            String description = (String) pluginYAML.get("description");
            return new PluginInfo(mainClass, name, description, true);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addCommand(PluginCommand command) {
        Minetweak.registerCommand(command.getName(), new BukkitCommandExecutor(command.getExecutor()));
        commands.put(command.getName(), command);
    }
}
