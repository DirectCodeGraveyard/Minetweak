package org.minetweak.dependencies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.minetweak.Minetweak;
import org.minetweak.util.HttpUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@SuppressWarnings("FieldCanBeLocal")
public class DependencyManager {

    public static Gson gson = new GsonBuilder().create();

    public static DependencyConfig config;

    private static File dependencyFolder = new File("lib/");

    private static String repoJson = "http://repo.minetweak.org/dependenciesList.json";
    private static File repoJsonLocal = new File("dependenciesList.json");

    private static String json;

    private static boolean localJsonDownloaded = repoJsonLocal.exists();

    public static void createDependenciesFolder() {
        if (dependencyFolder.exists()) return;

        if (!dependencyFolder.mkdir()) {
            Minetweak.getLogger().logWarning("Unable to create Dependency Folder!");
        }
    }

    public static void updateList() {
        Minetweak.getLogger().logInfo("Dependencies repo was updated");
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
    }

    public static boolean dependencyExistsLocally(String name, String version) {
        return new File("lib/" + name + "/" + version + "/" + name + ".jar").exists();
    }

    public static File retrieveDependency(String name, String version) {
        if (!localJsonDownloaded) return null;

        for (DependencyConfig.Dependency dep : config.dependencies) {
            if (dep.name.equals(name) && dep.currentVersion.equals(version)) {
                File folder = new File("lib/" + name + "/" + version + "/");
                File jar = new File(folder, name + ".jar");
                if (jar.exists()) return null;

                folder.mkdirs();

                Minetweak.getLogger().logInfo("Downloading dependency: " + name + " v" + version);
                HttpUtils.downloadFile(jar.getAbsolutePath(), dep.url);

                return jar;
            }
        }

        System.out.println("The dependency: " + name + " v" + version + " could not be downloaded.");
        return null;
    }

}
