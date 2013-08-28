package org.minetweak.plugins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import groovy.lang.Closure;
import groovy.lang.GroovyClassLoader;
import org.minetweak.Minetweak;
import org.minetweak.util.ReflectionUtils;
import org.minetweak.util.TweakLogger;

import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.zip.ZipEntry;

/**
 * Manages Plugins
 */
public class PluginManager {

    private ArrayList<File> files = new ArrayList<File>();
    public static GroovyClassLoader loader;
    public static HashMap<String, Object> plugins = new HashMap<String, Object>();
    public static ArrayList<String> enabledPlugins = new ArrayList<String>();
    private Gson gson = new GsonBuilder().create();
    private static ArrayList<String> loadFirst = new ArrayList<String>();

    /**
     * Creates an instance of PluginLoader and runs setupPlugins
     */
    public static void initialize() {
        PluginManager loader = new PluginManager();
        loader.setupPlugins();
        enableAll();
    }

    /**
     * Enable a plugin - must be loaded
     *
     * @param pluginName the plugin name
     */
    public static void enable(String pluginName) {
        if (doesPluginExist(pluginName)) {
            Object plugin = plugins.get(pluginName);
            ReflectionUtils.runFirstAnnotation(Plugin.Enable.class, plugin);
            enabledPlugins.add(pluginName);
        }
    }

    /**
     * Disable a plugin - must be enabled
     *
     * @param pluginName the plugin name
     */
    public static void disable(String pluginName) {

        if (isPluginEnabled(pluginName)) {
            Object plugin = plugins.get(pluginName);
            ReflectionUtils.runFirstAnnotation(Plugin.Disable.class, plugin);
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
            if (pluginInfo == null) {
                // This is probably a Library or a plugin without the required plugin.json
                Minetweak.getLogger().getLogger().log(Level.FINE, "Found JAR '" + f.getName() + "'. Appears to not have a plugin.json, assuming it is a library.");
                try {
                    urls.add(f.toURI().toURL());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                continue;
            } else if (pluginInfo.getMainClass() == null || pluginInfo.getName() == null) {
                Minetweak.getLogger().logWarning("Skipping Plugin JAR '" + f.getName() + "'. Missing Required plugin.json entries.");
            }
            pluginInformation.put(pluginInfo.getMainClass(), pluginInfo);
            PluginInfo.LoadingConfig loadingConfig = pluginInfo.getLoadingConfig();
            if (loadingConfig != null) {
                int priority = loadingConfig.getPriority();
                if (priority == 1) {
                    loadFirst.add(pluginInfo.getName());
                }
            }
            ArrayList<PluginInfo.SubPlugin> subPlugins = pluginInfo.getSubPlugins();
            if (subPlugins != null) {
                for (PluginInfo.SubPlugin subPlugin : subPlugins) {
                    PluginInfo subPluginInfo = new PluginInfo(subPlugin.getMainClass(), pluginInfo.getName() + ":" + subPlugin.getName(), null);
                    pluginInformation.put(subPlugin.getMainClass(), subPluginInfo);
                    classes.add(subPluginInfo.getMainClass());
                }
            }
            try {
                urls.add(f.toURI().toURL());
                classes.add(pluginInfo.getMainClass());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        loader = new GroovyClassLoader();
        for (URL url : urls) {
            loader.addURL(url);
        }
        for (String c : classes) {
            try {
                Class<?> pc = Class.forName(c, true, loader);
                Object plugin =  pc.newInstance();
                PluginInfo info = pluginInformation.get(c);

                TweakLogger logger = new TweakLogger(pluginInformation.get(c).getName());

                for (Field field : plugin.getClass().getFields()) {
                    if (ReflectionUtils.annotationExists(Plugin.Instance.class, field)) {
                        field.set(null, plugin);
                    }

                    if (ReflectionUtils.annotationExists(Plugin.Logger.class, field)) {
                        field.set(plugin, logger);
                    }

                    if (ReflectionUtils.annotationExists(Plugin.Info.class, field)) {
                        field.set(plugin, logger);
                    }
                }

                // Note that we override plugins even if they exist. This allows for alphabetical file-name plugin overriding
                plugins.put(pluginInformation.get(c).getName(), plugin);

                if (info.getLoadingConfig() != null && info.getLoadingConfig().isCorePlugin()) {
                    ReflectionUtils.runFirstAnnotation(Plugin.Load.class, plugin);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error loading plugin", e);
            }
        }
    }

    /**
     * Enable all Plugins
     */
    public static void enableAll() {
        new PluginLoadingThread().start();
    }

    /**
     * Gets plugin info
     *
     * @param file jar file
     * @return plugin info
     */
    private PluginInfo getPluginInfo(File file) {
        try {
            JarFile jf = new JarFile(file);
            ZipEntry entry = jf.getEntry("plugin.json");
            ZipEntry bukkitYaml = jf.getEntry("plugin.yml");
            ZipEntry langFolder = jf.getEntry("lang/");
            if (entry == null) {
                if (bukkitYaml != null) {
                    Minetweak.info("Found Bukkit Plugin in " + file.getName() + ". Skipping....");
                }
                return null;
            }
            if (langFolder != null) registerLanguageFiles(jf);
            return gson.fromJson(new InputStreamReader(jf.getInputStream(entry)), PluginInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of Plugin files
     * NOTE: No longer uses NIO
     */
    private void getPluginFiles() {
        File pluginDirectory = new File("./plugins/");
        if (!pluginDirectory.exists()) {
            if (!pluginDirectory.mkdirs()) {
                Minetweak.getLogger().logWarning("Unable to create plugin directory. Skipping Loading Plugins.");
                return;
            }
        }
        File[] fileList = pluginDirectory.listFiles();
        if (fileList == null) {
            return;
        }
        for (File file : fileList) {
            if (file.isDirectory()) {
                continue;
            }
            if (file.getName().endsWith(".jar")) {
                files.add(file);
            }
        }
    }

    /**
     * Disables all Plugins
     */
    public static void disableAll() {
        ArrayList<String> pluginsToDisable = new ArrayList<String>(enabledPlugins);
        for (String pluginName : pluginsToDisable) {
            disable(pluginName);
        }
    }

    /**
     * Disable plugins, then reload from plugins directory
     */
    public static void reloadPlugins() {
        disableAll();
        plugins.clear();
        enabledPlugins.clear();
        loader = null;
        loadFirst.clear();
        initialize();
    }

    /**
     * Creates the plugins directory
     */
    private void createDir() {
        File f = new File("plugins");
        if (!f.isDirectory() && !f.mkdir()) {
            throw new RuntimeException("Unable to create plugins folder!");
        }
    }

    /**
     * Detect if a plugin is enabled
     *
     * @param pluginName name of plugin
     * @return if the plugin is enabled
     */
    public static boolean isPluginEnabled(String pluginName) {
        return enabledPlugins.contains(pluginName);
    }

    /**
     * Detect if a plugin is registered. This does not mean they are enabled
     *
     * @param pluginName name of plugin
     * @return if the plugin is registered
     */
    public static boolean doesPluginExist(String pluginName) {
        return plugins.keySet().contains(pluginName);
    }

    public void registerLanguageFiles(JarFile file) {
        // Stub
    }

    public static ArrayList<String> getLoadFirstPlugins() {
        return loadFirst;
    }

    public static void eachPlugin(Closure closure) {
        for (Object plugin : plugins.values()) {
            closure.call(plugin);
        }
    }
}