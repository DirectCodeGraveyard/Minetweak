package org.bukkit.plugin;

import org.minetweak.plugins.PluginInfo;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class MinetweakHelper {
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
}
