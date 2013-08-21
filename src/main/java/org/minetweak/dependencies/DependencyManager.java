package org.minetweak.dependencies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import groovy.lang.GroovyClassLoader;
import org.apache.commons.io.IOUtils;
import org.minetweak.Minetweak;
import org.minetweak.util.HttpUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("FieldCanBeLocal")
public class DependencyManager {

    public static Gson gson = new GsonBuilder().create();

    public static DependencyConfig config;

    private static File dependencyFolder = new File("lib/");

    private static String repoJson = "http://repo.minetweak.org/dependenciesList.json";
    private static File repoJsonLocal = new File("dependenciesList.json");
    private static GroovyClassLoader classLoader = new GroovyClassLoader(ClassLoader.getSystemClassLoader());

    public static String json;

    private static boolean localJsonDownloaded = repoJsonLocal.exists();
    private static boolean localJsonParsed = false;

    public static void createDependenciesFolder() {
        if (dependencyFolder.exists()) return;

        if (!dependencyFolder.mkdir()) {
            Minetweak.getLogger().logWarning("Unable to create Dependency Folder!");
        }
    }

    public static void updateList() {
        // Minetweak.getLogger().info("Dependencies repo was updated");
        HttpUtils.downloadFile(repoJsonLocal.getAbsolutePath(), repoJson);
        localJsonDownloaded = true;
    }

    public static void readJson() {
        try {
            json = IOUtils.toString(new FileReader(repoJsonLocal));
        } catch (IOException e) {
            e.printStackTrace();
        }
        config = gson.fromJson(json, DependencyConfig.class);
        localJsonParsed = true;
    }

    public static boolean dependencyInstalled(String name, String version) {
        return new File("lib/" + name + "/" + version + "/" + name + ".jar").exists();
    }

    public static File retrieveDependency(String name, String version) {
        while (!localJsonDownloaded) ;

        for (DependencyConfig.Dependency dep : config.dependencies) {
            if (dep.name.equalsIgnoreCase(name) && dep.version.equalsIgnoreCase(version)) {
                File folder = new File("lib/" + name + "/" + version + "/");
                File jar = new File(folder, name + ".jar");
                if (jar.exists()) return jar;

                if (!folder.mkdirs()) {
                    Minetweak.getLogger().logWarning("Unable to make the directories for dependency \'" + dep.name + "\'!");
                    return null;
                }

                Minetweak.getLogger().info("Downloading dependency: " + name + " v" + version);
                HttpUtils.downloadFile(jar.getAbsolutePath(), dep.url);

                return jar;
            }
        }

        Minetweak.getLogger().logWarning("The dependency: " + name + " v" + version + " could not be downloaded.");
        return null;
    }

    public static void loadDependency(File file) {
        if (file==null) {
            Minetweak.getLogger().logSevere("Tried to load a null dependency file.");
            return;
        }
        try {
            loadDependency(file.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void loadDependency(URL url) {
        classLoader.addURL(url);
    }

    public static GroovyClassLoader getClassLoader() {
        return classLoader;
    }
}
