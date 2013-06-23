package org.minetweak.plugins;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class PluginLoader {

    private ArrayList<File> files = new ArrayList<File>();
    private URLClassLoader loader;
    public static HashMap<String, Plugin> plugins = new HashMap<String, Plugin>();
    public static PluginLoader instance = new PluginLoader();

    public static void initialize() {
        instance.setupPlugins();
    }

    public void setupPlugins() {
        createDir();
        getPluginFiles();
        ArrayList<String> classes = new ArrayList<String>();
        ArrayList<URL> urls = new ArrayList<URL>();
        for (File f : files) {
            Manifest mf = getManifest(f);
            String pluginClass = mf.getMainAttributes().getValue("Plugin-class");
            if (pluginClass == null) {
                continue;
            }
            try {
                urls.add(f.toURI().toURL());
                classes.add(pluginClass);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        loader = new URLClassLoader(urls.toArray(new URL[urls.size()]), this.getClass().getClassLoader());
        for (String c : classes) {
            try {
                Class pc = Class.forName(c, true, loader);
                Plugin plugin = (Plugin) pc.newInstance();
                if (plugins.containsKey(plugin.getName()))
                    throw new RuntimeException("Duplicate Plugins detected: " + plugin.getName());
            } catch (Exception e) {
                throw new RuntimeException("Error loading Plugins", e);
            }
        }
    }

    public static void enablePlugins() {
        for (Plugin plugin : plugins.values()) {
            plugin.onEnable();
        }
    }

    public static void disablePlugin(String name) {
        if (plugins.containsKey(name)) {
            plugins.get(name).onDisable();
        }
    }

    private Manifest getManifest(File f) {
        try {
            JarFile jf = new JarFile(f);
            return jf.getManifest();
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

    private void createDir() {
        File f = new File("plugins");
        if (!f.isDirectory()) {
            f.mkdir();
        }
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
