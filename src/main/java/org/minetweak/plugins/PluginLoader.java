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
    public static URLClassLoader loader;
    public static HashMap<String, MinetweakPlugin> plugins = new HashMap<String, MinetweakPlugin>();

    public static void initialize() {
        PluginLoader loader = new PluginLoader();
        loader.setupModules();
    }

    public void setupModules() {
        createDir();
        getModuleFiles();
        ArrayList<String> classes = new ArrayList<String>();
        ArrayList<URL> urls = new ArrayList<URL>();
        for (File f : files) {
            Manifest mf = getManifest(f);
            String moduleClass = mf.getMainAttributes().getValue("Plugin-class");
            if (moduleClass == null) {
                continue;
            }
            try {
                urls.add(f.toURI().toURL());
                classes.add(moduleClass);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        loader = new URLClassLoader(urls.toArray(new URL[urls.size()]), this.getClass().getClassLoader());
        for (String c : classes) {
            try {
                Class mc = Class.forName(c, true, loader);
                MinetweakPlugin plugin = (MinetweakPlugin) mc.newInstance();
                if (plugins.get(plugin.getName()) != null)
                    throw new RuntimeException("Duplicate modules detected: " + plugin.getName());
                plugins.put(plugin.getName(), plugin);
            } catch (Exception e) {
                throw new RuntimeException("Error loading plugin", e);
            }
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

    private void getModuleFiles() {
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
            if (!f.mkdir()) {
                throw new RuntimeException("Unable to create plugins folder!");
            }
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