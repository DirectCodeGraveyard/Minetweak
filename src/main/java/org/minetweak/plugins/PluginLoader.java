package org.minetweak.plugins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.minetweak.MinetweakHelper;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.minetweak.Minetweak;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class PluginLoader {

    private ArrayList<File> files = new ArrayList<File>();
    public static URLClassLoader loader;
    public static HashMap<String, IPlugin> plugins = new HashMap<String, IPlugin>();
    public static ArrayList<String> enabledPlugins = new ArrayList<String>();
    private Gson gson = new GsonBuilder().create();

    /**
     * Creates an instance of PluginLoader and runs setupPlugins
     */
    public static void initialize() {
        PluginLoader loader = new PluginLoader();
        loader.setupPlugins();
        enableAll();
    }

    /**
     * Enable a plugin - must be loaded
     * @param pluginName the plugin name
     */
    public static void enable(String pluginName) {
        if (plugins.containsKey(pluginName)) {
            new PluginLoadingThread(plugins.get(pluginName)).start();
        }
    }

    /**
     * Disable a plugin - must be enabled
     * @param pluginName the plugin name
     */
    public static void disable(String pluginName) {
        if (plugins.containsKey(pluginName)) {
            plugins.get(pluginName).onDisable();
            enabledPlugins.remove(pluginName);
        }
    }

    /**
     * Loads plugins
     */
    public void setupPlugins() {
        createDir();
        getPluginFiles();
        ArrayList<String> classes = new ArrayList<String>();
        ArrayList<URL> urls = new ArrayList<URL>();
        HashMap<String, PluginInfo> pluginInformation = new HashMap<String, PluginInfo>();
        for (File f : files) {
            PluginInfo pluginInfo = getPluginInfo(f);
            if (pluginInfo == null || pluginInfo.getMainClass()==null) {
                Minetweak.getLogger().logInfo("Skipping Plugin JAR: " + f.getName() + ": Missing plugin information or main class");
                continue;
            }
            pluginInformation.put(pluginInfo.getMainClass(), pluginInfo);
            try {
                urls.add(f.toURI().toURL());
                classes.add(pluginInfo.getMainClass());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        loader = new URLClassLoader(urls.toArray(new URL[urls.size()]), this.getClass().getClassLoader());
        for (String c : classes) {
            try {
                Class pc = Class.forName(c, true, loader);
                if (JavaPlugin.class.isInstance(pc)) {
                    Minetweak.info("Found Bukkit Plugin. Skipping until full support is added.");
                    continue;
                }
                IPlugin plugin = (IPlugin) pc.newInstance();
                plugin.setPluginInfo(pluginInformation.get(c));
                // Note that we override plugins even if they exist. This allows for alphabetical file-name plugin overriding
                plugins.put(plugin.getPluginInfo().getName(), plugin);
            } catch (Exception e) {
                throw new RuntimeException("Error loading plugin", e);
            }
        }
    }

    /**
     * Enable all Plugins
     */
    public static void enableAll() {
        for (String plugin : plugins.keySet()) {
            enable(plugin);
        }
    }

    /**
     * Gets plugin info
     * @param file jar file
     * @return plugin info
     */
    private PluginInfo getPluginInfo(File file) {
        try {
            JarFile jf = new JarFile(file);
            ZipEntry entry = jf.getEntry("plugin.json");
            ZipEntry bukkitYAML = jf.getEntry("plugin.yaml");
            if (entry==null) {
                if (bukkitYAML!=null) {
                    return MinetweakHelper.parsePluginYAML(jf);
                }
                return null;
            }
            return gson.fromJson(new InputStreamReader(jf.getInputStream(entry)), PluginInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of Plugin files
     */
    private void getPluginFiles() {
        try {
            FileVisitor<Path> visitor = new FileProcessor(this);
            Files.walkFileTree(Paths.get("plugins"), visitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Disables all Plugins
     */
    public static void disableAll() {
        for (IPlugin plugin : plugins.values()) {
            plugin.onDisable();
            enabledPlugins.remove(plugin.getPluginInfo().getName());
        }
    }

    /**
     * Disable plugins, then reload from plugins directory
     */
    public static void reloadPlugins() {
        disableAll();
        initialize();
    }

    /**
     * Creates the plugins directory
     */
    private void createDir() {
        File f = new File("plugins");
        if (!f.isDirectory()) {
            if (!f.mkdir()) {
                throw new RuntimeException("Unable to create plugins folder!");
            }
        }
    }

    /**
     * Detect if a plugin is enabled
     * @param pluginName name of plugin
     * @return if the plugin is enabled
     */
    public static boolean isPluginEnabled(String pluginName) {
        return enabledPlugins.contains(pluginName);
    }

    /**
     * Detect if a plugin is registered. This does not mean they are enabled
     * @param pluginName name of plugin
     * @return if the plugin is registered
     */
    public static boolean doesPluginExist(String pluginName) {
        return plugins.keySet().contains(pluginName);
    }

    private static final class FileProcessor extends SimpleFileVisitor<Path> {

        public PluginLoader instance;

        public FileProcessor(PluginLoader instance) {
            this.instance = instance;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException {
            if (path.toString().endsWith(".jar")) {
                instance.files.add(path.toFile());
            }
            return FileVisitResult.CONTINUE;
        }
    }
}