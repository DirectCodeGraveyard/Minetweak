package org.minetweak;

import com.google.common.eventbus.EventBus;
import net.minecraft.server.MinecraftServer;
import org.minetweak.command.*;
import org.minetweak.config.MinetweakConfig;
import org.minetweak.entity.Player;
import org.minetweak.entity.player.PlayerTracker;
import org.minetweak.permissions.PermissionsLoader;
import org.minetweak.plugins.PluginLoaderHook;
import org.minetweak.recipe.RecipeManager;
import org.minetweak.thread.ManagementThread;
import org.minetweak.util.MinetweakLog;

import java.io.File;
import java.util.HashMap;

/**
 * Main entry point for Minetweak, basically defines fields
 * for use throughout the internal server, and even the API
 * itself. It gives most of the basic methods that you will
 * need to create a plugin, like registering a command,
 * or an event.
 */
@SuppressWarnings("FieldCanBeLocal")
public class Minetweak {

    /**
     * This is the Minecraft version we are currently running on.
     */
    private static final String minecraftVersion = "1.6.2";

    /**
     * This is the Minetweak version we are currently running on.
     */
    private static final String serverVersion = "0.6.3";

    /**
     * This boolean will return true if the server has finished loading, will
     * be replaced by a ServerFinishedStartupEvent possibly.
     */
    private static boolean isServerDoneLoading = false;

    /**
     * This boolean will return true if the server is currently under lockdown,
     * and non-op players are kicked out.
     */
    private static boolean lockdownEnabled = false;

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

    private static MinetweakLog logger = new MinetweakLog("Minetweak", null, (new File("./", "minetweak.log")).getAbsolutePath());

    /**
     * Runs the Minetweak Server.
     * @param args the arguments to pass to MinecraftServer
     */
    public static void main(String[] args) {
        // Load the most important things first
        MinetweakConfig.initialize();
        PermissionsLoader.load();

        // Ensure Server Commands get registered first, so they can be overridden
        registerServerCommands();

        // Register Instances of Threads and Managers
        registerListener(RecipeManager.getInstance());
        registerListener(ManagementThread.getInstance());

        // Used to run plugin startup inside the Server
        registerListener(new PluginLoaderHook());

        // Loads joined player list
        registerListener(PlayerTracker.getInstance());

        // Checks RAM usage to ensure that the server has enough
        ramCheck();

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
     * @return Minecraft Version
     */
    public static String getMinecraftVersion() {
        return minecraftVersion;
    }

    /**
     * What Minetweak API version are we running?
     * @return Minetweak API Version
     */
    public static String getAPIVersion() {
        return serverVersion;
    }

    /**
     * Is the server done loading?
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
     * @return Lockdown status
     */
    public static boolean isServerLockedDown() {
        return lockdownEnabled;
    }

    /**
     * Get a specific player by their username, either online or offline, if they are online
     * @param playerName Player's username
     * @return Instance of player
     */
    public static Player getPlayerByName(String playerName) {
       return players.get(playerName.toLowerCase());
    }

    /**
     * Check to ensure that a command exists.
     * @param command Target command label
     * @return True if the command does exist
     */
    public static boolean doesCommandExist(String command) {
        return commandExecutors.containsKey(command);
    }

    /**
     * Get the class to the corresponding command label specified. Return null if no command exists with that label.
     * @param commandLabel PluginCommand label to get
     * @return CommandExecutor for specified command label
     */
    public static CommandExecutor getCommandByName(String commandLabel) {
        if (commandExecutors.containsKey(commandLabel)) return commandExecutors.get(commandLabel);
        else return null;
    }

    /**
     * Register a command within Minetweak
     * @param commandLabel Label that the command uses
     * @param commandExecutor CommandExecutor class that we will use to execute the command
     */
    public static void registerCommand(String commandLabel, CommandExecutor commandExecutor) {
        commandExecutors.put(commandLabel, commandExecutor);
    }

    /**
     * Get the EventBus that handles all events that go on in the server
     * @return Server EventBus
     */
    public static EventBus getEventBus() {
        return eventBus;
    }

    /**
     .* Registers a Guava Event Listener
     * @param object the instance of the listener
     */
    public static void registerListener(Object object) {
        eventBus.register(object);
    }

    /**
     * Check whether a player is on by username
     * @param playerUsername The players username
     * @return if the player is online
     */
    public static boolean isPlayerOnline(String playerUsername) {
        return players.containsKey(playerUsername);
    }

    /**
     * MinetweakLog Minetweak Info to Console
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
        registerCommand("deop", new CommandDeop());
        registerCommand("gamemode", new CommandGamemode());
        registerCommand("help", new CommandHelp());
        registerCommand("kick", new CommandKick());
        registerCommand("kill", new CommandKill());
        registerCommand("players", new CommandListPlayers());
        registerCommand("op", new CommandOp());
        registerCommand("say", new CommandSay());
        registerCommand("stop", new CommandStop());
        registerCommand("motd", new CommandMotd());
        registerCommand("pardon", new CommandPardon());
        registerCommand("reload", new CommandReload());
        registerCommand("loaddata", new CommandLoadData());
        registerCommand("clear", new CommandClearInv());
        registerCommand("version", new CommandVersion());
        registerCommand("give", new CommandGive());
        registerCommand("save", new CommandSave());
        registerCommand("weather", new CommandWeather());
        registerCommand("tps", new CommandTps());
        registerCommand("spawn", new CommandSpawn());
    }

    /**
     * Return the HashMap with the players. Key is the username, Value is the Player instance.
     * @return Players HashMap
     */
    public static HashMap<String, Player> getPlayers() {
        return players;
    }

    /**
     * Gets the PluginCommand Executors for Strings
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
     * @param label the commands name
     */
    public static void unregisterCommand(String label) {
        commandExecutors.remove(label);
    }
}
