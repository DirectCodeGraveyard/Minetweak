package org.minetweak.dependencies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import groovy.lang.GroovyClassLoader;
import org.apache.commons.io.IOUtils;
import org.minetweak.Minetweak;
import org.minetweak.util.HashUtils;
import org.minetweak.util.HttpUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("FieldCanBeLocal")
public class DependencyManager {

    private static Gson gson = new GsonBuilder().create();

    private static DependencyConfig config;

    private static File dependencyFolder = new File("lib/");

    private static String repoJson = "http://repo.minetweak.org/dependenciesList.json";
    private static File repoJsonLocal = new File("dependenciesList.json");
    private static GroovyClassLoader classLoader = new GroovyClassLoader(DependencyManager.class.getClassLoader());

    private static String json;

    private static boolean localJsonDownloaded = repoJsonLocal.exists();
    private static boolean localJsonParsed = false;

    /**
     * Creates the Dependencies Folder if it doesn't already exist
     */
    public static void createDependenciesFolder() {
        if (dependencyFolder.exists()) return;

        if (!dependencyFolder.mkdir()) {
            Minetweak.getLogger().logWarning("Unable to create Dependency Folder!");
        }
    }

    /**
     * Download the list then read it.
     */
    public static void updateList() {
        createDependenciesFolder();
        boolean wasDownloaded = HttpUtils.downloadFile(repoJsonLocal.getAbsolutePath(), repoJson);
        if (!wasDownloaded) {
            Minetweak.getLogger().logSevere("Unable to retrieve Dependency List.");
            return;
        }
        readJson();
        localJsonDownloaded = true;
        if (config.verbose) {
            Minetweak.getLogger().info("Dependencies repo was updated");
        }
    }

    /**
     * Reads the Dependency Configuration JSON File
     */
    public static void readJson() {
        if (!repoJsonLocal.exists()) {
            json = "{}";
        } else {
            try {
                json = IOUtils.toString(new FileReader(repoJsonLocal));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = gson.fromJson(json, DependencyConfig.class);
        localJsonParsed = true;
    }

    public static boolean dependencyInstalled(String name, String version) {
        return new File("lib/" + name + "/" + version + "/" + name + ".jar").exists();
    }

    /**
     * Downloads a Dependency
     *
     * @param name    name
     * @param version version
     * @return File downloaded to
     */
    public static File retrieveDependency(String name, String version) {
        while (!localJsonDownloaded) ;

        for (DependencyConfig.Dependency dep : config.dependencies) {
            if (dep.name.equalsIgnoreCase(name) && dep.version.equalsIgnoreCase(version)) {
                File folder = new File("lib/" + name + "/" + version + "/");
                File jar = new File(folder, name + ".jar");

                if (jar.exists() && dep.sha1 != null) {
                    if (!HashUtils.validateSHA1(jar, dep.sha1)) {
                        Minetweak.info("Re-downloading dependency: " + name + " v" + ". File Invalid.");
                    } else {
                        return jar;
                    }
                }

                if (!folder.exists() && !folder.mkdirs()) {
                    Minetweak.getLogger().logWarning("Unable to make the directories for dependency \'" + dep.name + "\'!");
                    return null;
                }

                Minetweak.getLogger().info("Downloading dependency: " + name + " v" + version);
                HttpUtils.downloadFile(jar.getAbsolutePath(), dep.url);

                if (dep.sha1 != null) {
                    if (!HashUtils.validateSHA1(jar, dep.sha1)) {
                        Minetweak.getLogger().logSevere("Unable to download dependency: " + name + " v" + version + ". File Invalid.");
                        return null;
                    }
                }

                return jar;
            }
        }

        Minetweak.getLogger().logWarning("The dependency: " + name + " v" + version + " could not be downloaded.");
        return null;
    }

    /**
     * Loads a Dependency via a File
     *
     * @param file File
     */
    public static void loadDependency(File file) {
        if (file == null) {
            Minetweak.getLogger().logSevere("Tried to load a null dependency file.");
            return;
        }
        try {
            loadDependency(file.toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a Dependency via a URL
     *
     * @param url URL
     */
    public static void loadDependency(URL url) {
        classLoader.addURL(url);
    }

    /**
     * Gets the ClassLoader used in Dependency Management
     *
     * @return ClassLoader
     */
    public static GroovyClassLoader getClassLoader() {
        return classLoader;
    }
}
