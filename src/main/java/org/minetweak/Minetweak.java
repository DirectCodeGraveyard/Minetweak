package org.minetweak;

import com.google.common.eventbus.EventBus;
import net.minecraft.server.MinecraftServer;
import org.minetweak.chat.TextColor;
import org.minetweak.command.*;
import org.minetweak.config.GameConfig;
import org.minetweak.dependencies.DependencyManager;
import org.minetweak.entity.Player;
import org.minetweak.entity.player.PlayerTracker;
import org.minetweak.permissions.PermissionsLoader;
import org.minetweak.permissions.PlayerWhitelist;
import org.minetweak.permissions.ServerOps;
import org.minetweak.plugins.PluginLoadingHook;
import org.minetweak.recipe.RecipeManager;
import org.minetweak.thread.ManagementThread;
import org.minetweak.util.MinetweakLog;
import org.minetweak.world.World;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * Main entry point for Minetweak.
 * It gives the basic methods that you
 * will need to create a plugin, such as:
 * registering a command, or an event.
 */
public class Minetweak {

    /**
     * Minecraft version
     */
    private static final String minecraftVersion = "1.6.2";

    /**
     * Minetweak API version
     */
    private static String serverVersion = "0.6.5";

    /**
     * This field returns true if the server has finished the startup process.
     */
    private static boolean isServerDoneLoading = false;

    /**
     * This boolean will return true if the server is currently under lockdown,
     * and non-op players are kicked out.
     */
    private static boolean isLockedDown = false;

    /**
     * This is where we store the player's information at, inside of the Player
     * class contains their NetServerHandler and EntityPlayerMP.
     */
    private static HashMap<String, Player> players = new HashMap<String, Player>();

    /**
     * This is where we store the command executors for our base commands, and even
     * for other plugins. It will need some work later on for duplicates, etc.
     */
    private static HashMap<String, CommandExecutor> commandExecutors = new HashMap<String, CommandExecutor>();

    /**
     * The Guava EventBus that allows us to create our own events. This is basically
     * the main part of our event system, allowing us to register/unregister listeners
     * and tell the EventBus that an event needs to be fired.
     */
    private static EventBus eventBus = new EventBus();

    private static MinetweakLog logger = new MinetweakLog("Minetweak", null);

    /**
     * Runs the Minetweak Server.
     *
     * @param args the arguments to pass to MinecraftServer
     */
    public static void main(String[] args) {
        // Runs a quick utility to get the Server Version
        versionCheck();
        Minetweak.info("Starting Minetweak v" + serverVersion + " implementing Minecraft v" + getMinecraftVersion());
        // Load the most important things first
        GameConfig.initialize();
        PermissionsLoader.load();
        PlayerWhitelist.load();
        ServerOps.load();

        // Parse dependencies outside of the thread to ensure it works properly
        DependencyManager.createDependenciesFolder();
        DependencyManager.updateList();
        DependencyManager.readJson();

        // Ensure Server Commands get registered first, so they can be overridden
        registerServerCommands();

        // Register Instances of Threads and Managers
        registerListener(RecipeManager.getInstance());
        registerListener(ManagementThread.getInstance());

        // Used to run plugin startup inside the Server
        registerListener(new PluginLoadingHook());

        // Loads joined player list
        registerListener(PlayerTracker.getInstance());

        // Checks RAM usage to ensure that the server has enough
        ramCheck();

        // Initialize TextColor colorNodes ArrayList that contains the symbol and node
        TextColor.initializeColorNodes();

        // Finally, launch the Minecraft Server
        MinecraftServer.main(args);
    }

    private static void ramCheck() {
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            System.out.println("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar MinetweakLauncher.jar\"");
        }
    }

    /**
     * What Minecraft version are we running?
     *
     * @return Minecraft Version
     */
    public static String getMinecraftVersion() {
        return minecraftVersion;
    }

    /**
     * What Minetweak API version are we running?
     *
     * @return Minetweak API Version
     */
    public static String getAPIVersion() {
        return serverVersion;
    }

    /**
     * Check if the server is done loading.
     *
     * @return Server done loading
     */
    public static boolean isServerDoneLoading() {
        return isServerDoneLoading;
    }

    /**
     * Tell Minetweak that the Minecraft server itself has finished loading
     */
    public static void setServerDoneLoading() {
        isServerDoneLoading = true;
    }

    /**
     * Check to see if the server is in "lockdown" mode
     *
     * @return Lockdown status
     */
    public static boolean isServerLockedDown() {
        return isLockedDown;
    }

    /**
     * Get a specific player by their username, either online or offline, if they are online
     *
     * @param playerName Player's username
     * @return Instance of player
     */
    public static Player getPlayerByName(String playerName) {
        return players.get(playerName.toLowerCase());
    }

    /**
     * Check to ensure that a command exists.
     *
     * @param command Target command label
     * @return True if the command does exist
     */
    public static boolean doesCommandExist(String command) {
        return commandExecutors.containsKey(command);
    }

    /**
     * Get the class to the corresponding command label specified. Return null if no command exists with that label.
     *
     * @param commandLabel PluginCommand label to get
     * @return CommandExecutor for specified command label
     */
    public static CommandExecutor getCommandByName(String commandLabel) {
        if (commandExecutors.containsKey(commandLabel)) return commandExecutors.get(commandLabel);
        else return null;
    }

    /**
     * Register a command within Minetweak
     *
     * @param commandLabel    Label that the command uses
     * @param commandExecutor CommandExecutor class that we will use to execute the command
     */
    public static void registerCommand(String commandLabel, CommandExecutor commandExecutor) {
        commandExecutors.put(commandLabel, commandExecutor);
    }

    /**
     * Get the EventBus that handles all events that go on in the server
     *
     * @return Server EventBus
     */
    public static EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Registers a Guava Event Listener
     *
     * @param object the instance of the listener
     */
    public static void registerListener(Object object) {
        eventBus.register(object);
    }

    /**
     * Check whether a player is on by username
     *
     * @param playerUsername The players username
     * @return if the player is online
     */
    public static boolean isPlayerOnline(String playerUsername) {
        return players.containsKey(playerUsername);
    }

    /**
     * MinetweakLog Minetweak Info to Console
     *
     * @param line line to log
     */
    public static void info(String line) {
        getLogger().logInfo(line);
    }

    /**
     * Registers Default Server Commands
     */
    protected static void registerServerCommands() {
        registerCommand("ban", new CommandBan());
        registerCommand("clear", new CommandClearInventory());
        registerCommand("debug", new CommandDebug());
        registerCommand("deop", new CommandDeop());
        registerCommand("difficulty", new CommandDifficulty());
        registerCommand("gamemode", new CommandGamemode());
        registerCommand("give", new CommandGive());
        registerCommand("help", new CommandHelp());
        registerCommand("kick", new CommandKick());
        registerCommand("kill", new CommandKill());
        registerCommand("say", new CommandSay());
        registerCommand("save", new CommandSave());
        registerCommand("stop", new CommandStop());
        registerCommand("loaddata", new CommandLoadData());
        registerCommand("lockdown", new CommandLockdown());
        registerCommand("motd", new CommandMotd());
        registerCommand("op", new CommandOp());
        registerCommand("pardon", new CommandPardon());
        registerCommand("players", new CommandListPlayers());
        registerCommand("plugin", new CommandPlugin());
        registerCommand("reload", new CommandReload());
        registerCommand("setspawn", new CommandSetSpawn());
        registerCommand("slaughter", new CommandSlaughter());
        registerCommand("spawn", new CommandSpawn());
        registerCommand("time", new CommandTime());
        registerCommand("tp", new CommandTeleport());
        registerCommand("tpall", new CommandTeleportAll());
        registerCommand("tps", new CommandTps());
        registerCommand("version", new CommandVersion());
        registerCommand("viewinv", new CommandViewInventory());
        registerCommand("weather", new CommandWeather());
        registerCommand("whitelist", new CommandWhitelist());
    }

    /**
     * Return the HashMap with the players. Key is the username, Value is the Player instance.
     *
     * @return Players HashMap
     */
    public static HashMap<String, Player> getPlayers() {
        return players;
    }

    /**
     * Gets the PluginCommand Executors for Strings
     *
     * @return a HashMap of the commands to their executors
     */
    public static HashMap<String, CommandExecutor> getCommandExecutors() {
        return commandExecutors;
    }

    /**
     * Gets the Minetweak Logger
     */
    public static MinetweakLog getLogger() {
        return logger;
    }

    /**
     * Un-registers a command
     *
     * @param label the commands name
     */
    public static void unregisterCommand(String label) {
        commandExecutors.remove(label);
    }

    /**
     * Perform a version check
     */
    private static void versionCheck() {
        URL url = Minetweak.class.getProtectionDomain().getCodeSource().getLocation();
        boolean isJar = url.getFile().endsWith(".jar");

        if (isJar) {
            try {
                try {
                    JarFile file = new JarFile(new File(url.toURI()));
                    Attributes attributes = file.getManifest().getMainAttributes();
                    String version = attributes.getValue("Minetweak-version");
                    if (version != null) {
                        serverVersion = version;
                    }
                } catch (URISyntaxException ignored) {
                }
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Set the Server lockdown mode
     *
     * @param lockdownEnabled True if the server is locked down
     */
    public static void setLockedDown(boolean lockdownEnabled) {
        isLockedDown = lockdownEnabled;
    }

    /**
     * Get the Overworld WorldServer
     *
     * @return Overworld WorldServer
     */
    public static World getOverworld() {
        return new World(MinecraftServer.getServer().worldServerForDimension(0));
    }
}
