package org.minetweak.plugins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class PluginLoader {

    private ArrayList<File> files = new ArrayList<File>();
    public static URLClassLoader loader;
    public static HashMap<String, MinetweakPlugin> plugins = new HashMap<String, MinetweakPlugin>();
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

    public static void enable(String pluginName) {
        if (plugins.containsKey(pluginName)) {
            plugins.get(pluginName).onEnable();
            enabledPlugins.add(pluginName);
        }
    }

    public static void disable(String pluginName) {
        if (plugins.containsKey(pluginName)) {
            plugins.get(pluginName).onDisable();
            enabledPlugins.remove(pluginName);
        }
    }

    public void setupPlugins() {
        createDir();
        getPluginFiles();
        ArrayList<String> classes = new ArrayList<String>();
        ArrayList<URL> urls = new ArrayList<URL>();
        for (File f : files) {
            PluginInfo pluginInfo = getPluginInfo(f);
            if (pluginInfo == null || pluginInfo.getMainClass()==null) {
                continue;
            }
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
                Class mc = Class.forName(c, true, loader);
                MinetweakPlugin plugin = (MinetweakPlugin) mc.newInstance();
                // Note that we override plugins even if they exist. This allows for alphabetical file-name plugin overriding
                plugins.put(plugin.getName(), plugin);
            } catch (Exception e) {
                throw new RuntimeException("Error loading plugin", e);
            }
        }
    }

    public static void enableAll() {
        for (MinetweakPlugin plugin : plugins.values()) {
            plugin.onEnable();
            enabledPlugins.add(plugin.getName());
        }
    }

    private PluginInfo getPluginInfo(File file) {
        try {
            JarFile jf = new JarFile(file);
            ZipEntry entry = jf.getEntry("plugin.json");
            if (entry==null) {
                return null;
            }
            return gson.fromJson(new InputStreamReader(jf.getInputStream(entry)), PluginInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getPluginFiles() {
        try {
            FileVisitor<Path> visitor = new FileProcessor(this);
            Files.walkFileTree(Paths.get("plugins"), visitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void disableAll() {
        for (String pluginName : enabledPlugins) {
            disable(pluginName);
        }
    }

    /**
     * Disable then re-enable all plugins
     */
    public static void reloadPlugins() {
        disableAll();
        PluginLoader loader = new PluginLoader();
        loader.setupPlugins();
    }

    private void createDir() {
        File f = new File("plugins");
        if (!f.isDirectory()) {
            if (!f.mkdir()) {
                throw new RuntimeException("Unable to create plugins folder!");
            }
        }
    }

    public static boolean isPluginEnabled(String pluginName) {
        return enabledPlugins.contains(pluginName);
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