package net.minecraft.server;

import net.minecraft.block.behavior.DispenserBehaviors;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.exception.MinecraftException;
import net.minecraft.entity.EntityPlayer;
import net.minecraft.logging.ILogAgent;
import net.minecraft.player.IPlayerUsage;
import net.minecraft.player.PlayerUsageSnooper;
import net.minecraft.server.network.NetworkListenThread;
import net.minecraft.server.network.packet.Packet;
import net.minecraft.server.network.packet.Packet4UpdateTime;
import net.minecraft.server.rcon.RConConsoleSource;
import net.minecraft.src.*;
import net.minecraft.stats.StatList;
import net.minecraft.utils.AxisAlignedBB;
import net.minecraft.utils.MathHelper;
import net.minecraft.utils.callable.CallableIsServerModded;
import net.minecraft.utils.callable.CallableServerMemoryStats;
import net.minecraft.utils.callable.CallableServerProfiler;
import net.minecraft.utils.chat.ChatMessageComponent;
import net.minecraft.utils.enums.EnumGameType;
import net.minecraft.world.*;
import net.minecraft.world.anvil.AnvilSaveConverter;
import net.minecraft.world.chunk.ChunkCoordinates;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import org.minetweak.Minetweak;
import org.minetweak.event.server.WorldsLoadingEvent;
import org.minetweak.server.TickScheduler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public abstract class MinecraftServer implements ICommandSender, Runnable, IPlayerUsage {
    /**
     * Instance of Minecraft Server.
     */
    private static MinecraftServer mcServer;
    private final ISaveFormat anvilConverter;

    /**
     * The PlayerUsageSnooper instance.
     */
    private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("server", this, getCurrentMillis());
    private final File anvilFile;

    /**
     * List of names of players who are online.
     */
    private final List<IUpdatePlayerListBox> playersOnline = new ArrayList<IUpdatePlayerListBox>();
    private final ICommandManager commandManager;
    public final Profiler profiler = new Profiler();

    /**
     * The server's hostname.
     */
    private String hostname;

    /**
     * The server's port.
     */
    private int serverPort = -1;

    /**
     * The server world instances.
     */
    public WorldServer[] worldServers;

    /**
     * The ServerConfigurationManager instance.
     */
    private ServerConfigurationManager serverConfigManager;

    /**
     * Indicates whether the server is running or not. Set to false to initiate a shutdown.
     */
    private boolean serverRunning = true;

    /**
     * Indicates to other classes that the server is safely stopped.
     */
    private boolean serverStopped;

    /**
     * Incremented every tick.
     */
    private int tickCounter;
    protected Proxy proxy;

    /**
     * The task the server is currently working on(and will output on outputPercentRemaining).
     */
    public String currentTask;

    /**
     * The percentage of the current task finished so far.
     */
    public int percentDone;

    /**
     * True if the server is in online mode.
     */
    private boolean onlineMode;

    /**
     * True if the server has animals turned on.
     */
    private boolean canSpawnAnimals;
    private boolean canSpawnNPCs;

    /**
     * Indicates whether PvP is active on the server or not.
     */
    private boolean pvpEnabled;

    /**
     * Determines if flight is allowed or not.
     */
    private boolean allowFlight;

    /**
     * The server MOTD string.
     */
    private String motd;

    /**
     * Maximum build height.
     */
    private int buildLimit;
    private long lastSentPacketID;
    private long lastSentPacketSize;
    private long lastReceivedID;
    private long lastReceivedSize;
    public final long[] sentPacketCountArray;
    public final long[] sentPacketSizeArray;
    public final long[] receivedPacketCountArray;
    public final long[] receivedPacketSizeArray;
    public final long[] tickTimeArray;

    /**
     * Stats are [dimension][tick%100] system.nanoTime is stored.
     */
    public long[][] timeOfLastDimensionTick;
    private KeyPair serverKeyPair;

    private String folderName;
    private boolean enableBonusChest;

    /**
     * If true, there is no need to save chunks or stop the server, because that is already being done.
     */
    private boolean worldIsBeingDeleted;
    private String texturePack;
    private boolean serverIsRunning;

    /**
     * Set when warned for "Can't keep up", which triggers again after 15 seconds.
     */
    private long timeOfLastWarning;
    private String userMessage;
    private boolean startProfiling;
    private boolean field_104057_T;

    public MinecraftServer(File par1File) {
        this.proxy = Proxy.NO_PROXY;
        this.sentPacketCountArray = new long[100];
        this.sentPacketSizeArray = new long[100];
        this.receivedPacketCountArray = new long[100];
        this.receivedPacketSizeArray = new long[100];
        this.tickTimeArray = new long[100];
        this.texturePack = "";
        mcServer = this;
        this.anvilFile = par1File;
        this.commandManager = new ServerCommandManager();
        this.anvilConverter = new AnvilSaveConverter(par1File);
        this.registerDispenseBehaviors();
    }

    /**
     * Register all dispense behaviors.
     */
    private void registerDispenseBehaviors() {
        DispenserBehaviors.registerBehaviors();
    }

    /**
     * Initialises the server and starts it.
     */
    protected abstract boolean startServer() throws IOException;

    protected void convertMapIfNeeded(String par1Str) {
        if (this.getActiveAnvilConverter().isOldMapFormat(par1Str)) {
            this.getLogAgent().logInfo("Converting map!");
            this.getActiveAnvilConverter().convertMapFormat(par1Str, new ConvertingProgressUpdate(this));
        }
    }

    protected void loadAllWorlds(String folderName, String par2Str, long par3, WorldType par5WorldType, String par6Str) {
        this.convertMapIfNeeded(folderName);
        this.worldServers = new WorldServer[10];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
        ISaveHandler saveHandler = this.anvilConverter.getSaveLoader(folderName, true);
        WorldInfo worldInfo = saveHandler.loadWorldInfo();
        WorldSettings worldSettings;
        Minetweak.getEventBus().post(new WorldsLoadingEvent());

        if (worldInfo == null) {
            worldSettings = new WorldSettings(par3, this.getGameType(), this.canStructuresSpawn(), this.isHardcore(), par5WorldType);
            worldSettings.func_82750_a(par6Str);
        } else {
            worldSettings = new WorldSettings(worldInfo);
        }

        if (this.enableBonusChest) {
            worldSettings.enableBonusChest();
        }

        for (int var10 = 0; var10 < this.worldServers.length; ++var10) {
            byte var11 = 0;

            if (var10 == 1) {
                var11 = -1;
            }

            if (var10 == 2) {
                var11 = 1;
            }
            WorldServer server;
            if (var10 == 0) {
                server = new WorldServer(this, saveHandler, par2Str, var11, worldSettings, this.profiler, this.getLogAgent());
            } else {
                server = new WorldServerMulti(this, saveHandler, par2Str, var11, worldSettings, this.worldServers[0], this.profiler, this.getLogAgent());
            }

            server.addWorldAccess(new WorldManager(this, server));

            server.getWorldInfo().setGameType(this.getGameType());

            org.minetweak.world.WorldManager.addWorldServer(server);

            this.serverConfigManager.setPlayerManager(this.worldServers);
        }

        this.setDifficultyForAllWorlds(this.getDifficulty());
        this.initialWorldChunkLoad();
    }

    protected void initialWorldChunkLoad() {
        int var5 = 0;
        byte var6 = 0;
        WorldServer var7 = this.worldServers[var6];
        ChunkCoordinates var8 = var7.getSpawnPoint();
        long var9 = getCurrentMillis();

        for (int var11 = -192; var11 <= 192 && this.isServerRunning(); var11 += 16) {
            for (int var12 = -192; var12 <= 192 && this.isServerRunning(); var12 += 16) {
                long var13 = getCurrentMillis();

                ++var5;
                var7.theChunkProviderServer.loadChunk(var8.posX + var11 >> 4, var8.posZ + var12 >> 4);
            }
        }

        this.clearCurrentTask();
    }

    public abstract boolean canStructuresSpawn();

    public abstract EnumGameType getGameType();

    /**
     * Defaults to "1" (Easy) for the dedicated server, defaults to "2" (Normal) on the client.
     */
    public abstract int getDifficulty();

    /**
     * Defaults to false.
     */
    public abstract boolean isHardcore();

    public int getOpPermissionLevel() {
        return 4;
    }

    /**
     * Used to display a percent remaining given text and the percentage.
     */
    protected void outputPercentRemaining(String par1Str, int par2) {
        this.currentTask = par1Str;
        this.percentDone = par2;
        this.getLogAgent().logInfo(par1Str + ": " + par2 + "%");
    }

    /**
     * Set current task to null and set its percentage to 0.
     */
    protected void clearCurrentTask() {
        this.currentTask = null;
        this.percentDone = 0;
    }

    /**
     * par1 indicates if a log message should be output.
     */
    protected void saveAllWorlds(boolean par1) {
        if (!this.worldIsBeingDeleted) {
            WorldServer[] var2 = this.worldServers;
            int var3 = var2.length;

            for (WorldServer var5 : var2) {
                if (var5 != null) {
                    try {
                        var5.saveAllChunks(true, null);
                    } catch (MinecraftException var7) {
                        this.getLogAgent().logWarning(var7.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Saves all necessary data as preparation for stopping the server.
     */
    public void stopServer() {
        if (!this.worldIsBeingDeleted) {
            if (this.getNetworkThread() != null) {
                this.getNetworkThread().stopListening();
            }

            if (this.serverConfigManager != null) {
                this.serverConfigManager.saveAllPlayerData();
                this.serverConfigManager.removeAllPlayers();
            }

            this.saveAllWorlds(false);

            for (WorldServer var2 : this.worldServers) {
                var2.flush();
            }

            if (this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.stopSnooper();
            }
        }
    }

    /**
     * "getHostname" is already taken, but both return the hostname.
     */
    public String getServerHostname() {
        return this.hostname;
    }

    public void setHostname(String par1Str) {
        this.hostname = par1Str;
    }

    public boolean isServerRunning() {
        return this.serverRunning;
    }

    /**
     * Sets the serverRunning variable to false, in order to get the server to shut down.
     */
    public void initiateShutdown() {
        this.serverRunning = false;
    }

    @Override
    public void run() {
        try {
            if (this.startServer()) {
                long var1 = getCurrentMillis();

                for (long var50 = 0L; this.serverRunning; this.serverIsRunning = true) {
                    long var5 = getCurrentMillis();
                    long var7 = var5 - var1;

                    if (var7 > 2000L && var1 - this.timeOfLastWarning >= 15000L) {
                        this.getLogAgent().logInfo("Can\'t keep up! Did the system time change, or is the server overloaded?");
                        var7 = 2000L;
                        this.timeOfLastWarning = var1;
                    }

                    if (var7 < 0L) {
                        this.getLogAgent().logWarning("Time ran backwards! Did the system time change?");
                        var7 = 0L;
                    }

                    var50 += var7;
                    var1 = var5;

                    if (this.worldServers[0].areAllPlayersAsleep()) {
                        this.tick();
                        var50 = 0L;
                    } else {
                        while (var50 > 50L) {
                            var50 -= 50L;
                            this.tick();
                        }
                    }

                    Thread.sleep(1L);
                }
            } else {
                this.finalTick(null);
            }
        } catch (Throwable var48) {
            var48.printStackTrace();
            this.getLogAgent().logSevereException("Encountered an unexpected exception " + var48.getClass().getSimpleName(), var48);
            CrashReport var2;

            if (var48 instanceof ReportedException) {
                var2 = this.addServerInfoToCrashReport(((ReportedException) var48).getCrashReport());
            } else {
                var2 = this.addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", var48));
            }

            File var3 = new File(new File(this.getDataDirectory(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");

            if (var2.saveToFile(var3, this.getLogAgent())) {
                this.getLogAgent().logSevere("Crash report saved to: " + var3.getAbsolutePath());
            } else {
                this.getLogAgent().logSevere("Crash report could not be saved");
            }

            this.finalTick(var2);
        } finally {
            try {
                this.stopServer();
                this.serverStopped = true;
            } catch (Throwable var46) {
                var46.printStackTrace();
            } finally {
                System.exit(0);
            }
        }
    }

    protected File getDataDirectory() {
        return new File(".");
    }

    /**
     * Called on exit from the main run() loop.
     */
    protected void finalTick(CrashReport par1CrashReport) {
    }

    /**
     * Main function called by run() every loop.
     */
    protected void tick() {
        long var1 = System.nanoTime();
        AxisAlignedBB.getAABBPool().cleanPool();
        ++this.tickCounter;

        this.updateTimeLightAndEntities();

        if (this.tickCounter % 900 == 0) {
            this.serverConfigManager.saveAllPlayerData();
            this.saveAllWorlds(true);
        }

        this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - var1;
        this.sentPacketCountArray[this.tickCounter % 100] = Packet.sentID - this.lastSentPacketID;
        this.lastSentPacketID = Packet.sentID;
        this.sentPacketSizeArray[this.tickCounter % 100] = Packet.sentSize - this.lastSentPacketSize;
        this.lastSentPacketSize = Packet.sentSize;
        this.receivedPacketCountArray[this.tickCounter % 100] = Packet.receivedID - this.lastReceivedID;
        this.lastReceivedID = Packet.receivedID;
        this.receivedPacketSizeArray[this.tickCounter % 100] = Packet.receivedSize - this.lastReceivedSize;
        this.lastReceivedSize = Packet.receivedSize;
        TickScheduler.tick();
    }

    public void updateTimeLightAndEntities() {
        int var1;

        for (var1 = 0; var1 < this.worldServers.length; ++var1) {
            long var2 = System.nanoTime();

            if (var1 == 0 || this.getAllowNether()) {
                WorldServer var4 = this.worldServers[var1];
                var4.getWorldVec3Pool().clear();

                if (this.tickCounter % 20 == 0) {
                    this.serverConfigManager.sendPacketToAllPlayersInDimension(new Packet4UpdateTime(var4.getTotalWorldTime(), var4.getWorldTime(), var4.getGameRules().getGameRuleBooleanValue("doDaylightCycle")), var4.provider.dimensionId);
                }

                CrashReport var6;

                try {
                    var4.tick();
                } catch (Throwable var8) {
                    var6 = CrashReport.makeCrashReport(var8, "Exception ticking world");
                    var4.addWorldInfoToCrashReport(var6);
                    throw new ReportedException(var6);
                }

                try {
                    var4.updateEntities();
                } catch (Throwable var7) {
                    var6 = CrashReport.makeCrashReport(var7, "Exception ticking world entities");
                    var4.addWorldInfoToCrashReport(var6);
                    throw new ReportedException(var6);
                }

                var4.getEntityTracker().updateTrackedEntities();
            }

            this.timeOfLastDimensionTick[var1][this.tickCounter % 100] = System.nanoTime() - var2;
        }

        this.getNetworkThread().handleNetworkListenThread();
        this.serverConfigManager.onTick();

        for (var1 = 0; var1 < this.playersOnline.size(); ++var1) {
            (this.playersOnline.get(var1)).update();
        }
    }

    public boolean getAllowNether() {
        return true;
    }

    public void func_82010_a(IUpdatePlayerListBox par1IUpdatePlayerListBox) {
        this.playersOnline.add(par1IUpdatePlayerListBox);
    }

    public static void main(String[] par0ArrayOfStr) {
        StatList.nopInit();
        ILogAgent logAgent = null;

        try {
            boolean var2 = !GraphicsEnvironment.isHeadless();
            String var3 = null;
            String var4 = ".";
            String var5 = null;
            boolean var6 = false;
            boolean var7 = false;
            int var8 = -1;

            for (int var9 = 0; var9 < par0ArrayOfStr.length; ++var9) {
                String var10 = par0ArrayOfStr[var9];
                String var11 = var9 == par0ArrayOfStr.length - 1 ? null : par0ArrayOfStr[var9 + 1];
                boolean var12 = false;

                if (var10.equals("--bonusChest")) {
                    var7 = true;
                }

                ++var9;
            }

            DedicatedServer var16 = new DedicatedServer(new File(var4));
            logAgent = var16.getLogAgent();

            if (var5 != null) {
                var16.setFolderName(var5);
            }

            if (var8 >= 0) {
                var16.setServerPort(var8);
            }

            if (var7) {
                var16.canCreateBonusChest(true);
            }

            var16.startServerThread();
            Runtime.getRuntime().addShutdownHook(new ThreadStopDedicatedServer(var16));
        } catch (Exception var15) {
            if (logAgent != null) {
                logAgent.logSevereException("Failed to start the server.", var15);
            } else {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to start the server.", var15);
            }
        }
    }

    public void startServerThread() {
        (new ThreadMinecraftServer(this, "Server thread")).start();
    }

    /**
     * Returns a File object from the specified string.
     */
    public File getFile(String par1Str) {
        return new File(this.getDataDirectory(), par1Str);
    }

    /**
     * Logs the message with a level of INFO with prefix Minetweak.
     */
    public void logInfo(String par1Str) {
        this.getLogAgent().logInfo(par1Str);
    }

    /**
     * Logs the message with a level of WARN.
     */
    public void logWarning(String par1Str) {
        this.getLogAgent().logWarning(par1Str);
    }

    /**
     * Gets the worldServer by the given dimension.
     */
    public WorldServer worldServerForDimension(int par1) {
        return par1 == -1 ? this.worldServers[1] : (par1 == 1 ? this.worldServers[2] : this.worldServers[0]);
    }

    /**
     * Returns the server's hostname.
     */
    public String getHostname() {
        return this.hostname;
    }

    /**
     * Never used, but "getServerPort" is already taken.
     */
    public int getPort() {
        return this.serverPort;
    }

    /**
     * Returns the server message of the day
     */
    public String getMotd() {
        return this.motd;
    }

    /**
     * Returns the server's Minecraft version as string.
     */
    public String getMinecraftVersion() {
        return Minetweak.getMinecraftVersion();
    }

    /**
     * Returns the number of players currently on the server.
     */
    public int getCurrentPlayerCount() {
        return this.serverConfigManager.getCurrentPlayerCount();
    }

    /**
     * Returns the maximum number of players allowed on the server.
     */
    public int getMaxPlayers() {
        return this.serverConfigManager.getMaxPlayers();
    }

    /**
     * Returns an array of the usernames of all the connected players.
     */
    public String[] getAllUsernames() {
        return this.serverConfigManager.getAllUsernames();
    }

    /**
     * Used by RCon's Query in the form of "MajorServerMod 1.2.3: MyPlugin 1.3; AnotherPlugin 2.1; AndSoForth 1.0".
     */
    public String getPlugins() {
        return "Minetweak " + Minetweak.getAPIVersion();
    }

    /**
     * Handle a command received by an RCon instance
     */
    public String handleRConCommand(String par1Str) {
        RConConsoleSource.instance.resetLog();
        this.commandManager.executeCommand(RConConsoleSource.instance, par1Str);
        return RConConsoleSource.instance.getLogContents();
    }

    /**
     * Returns true if debugging is enabled, false otherwise.
     */
    public boolean isDebuggingEnabled() {
        return false;
    }

    /**
     * Logs the error message with a level of SEVERE.
     */
    public void logSevere(String par1Str) {
        this.getLogAgent().logSevere(par1Str);
    }

    /**
     * If isDebuggingEnabled(), logs the message with a level of INFO.
     */
    public void logDebug(String par1Str) {
        if (this.isDebuggingEnabled()) {
            this.getLogAgent().logInfo(par1Str);
        }
    }

    public String getServerModName() {
        return "minetweak";
    }

    /**
     * Adds the server info, including from theWorldServer, to the crash report.
     */
    public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport) {
        par1CrashReport.func_85056_g().addCrashSectionCallable("Profiler Position", new CallableIsServerModded(this));

        if (this.worldServers != null && this.worldServers.length > 0 && this.worldServers[0] != null) {
            par1CrashReport.func_85056_g().addCrashSectionCallable("Vec3 Pool Size", new CallableServerProfiler(this));
        }

        if (this.serverConfigManager != null) {
            par1CrashReport.func_85056_g().addCrashSectionCallable("Player Count", new CallableServerMemoryStats(this));
        }

        return par1CrashReport;
    }

    /**
     * Gets mcServer.
     */
    public static MinecraftServer getServer() {
        return mcServer;
    }

    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    @Override
    public String getCommandSenderName() {
        return "Server";
    }

    @Override
    public void func_110122_a(ChatMessageComponent par1ChatMessageComponent) {
        this.getLogAgent().logInfo(par1ChatMessageComponent.toString());
    }

    /**
     * Returns true if the command sender is allowed to use the given command.
     */
    @Override
    public boolean canCommandSenderUseCommand(int par1, String par2Str) {
        return true;
    }

    public ICommandManager getCommandManager() {
        return this.commandManager;
    }

    /**
     * Gets KeyPair instanced in MinecraftServer.
     */
    public KeyPair getKeyPair() {
        return this.serverKeyPair;
    }

    /**
     * Gets serverPort.
     */
    public int getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(int par1) {
        this.serverPort = par1;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String par1Str) {
        this.folderName = par1Str;
    }

    public void setKeyPair(KeyPair par1KeyPair) {
        this.serverKeyPair = par1KeyPair;
    }

    public void setDifficultyForAllWorlds(int par1) {
        for (WorldServer var3 : this.worldServers) {
            if (var3 != null) {
                if (var3.getWorldInfo().isHardcoreModeEnabled()) {
                    var3.difficultySetting = 3;
                    var3.setAllowedSpawnTypes(true, true);
                } else {
                    var3.difficultySetting = par1;
                    var3.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
                }
            }
        }
    }

    protected boolean allowSpawnMonsters() {
        return true;
    }

    public void canCreateBonusChest(boolean par1) {
        this.enableBonusChest = par1;
    }

    public ISaveFormat getActiveAnvilConverter() {
        return this.anvilConverter;
    }

    /**
     * WARNING : directly calls
     * getActiveAnvilConverter().deleteWorldDirectory(theWorldServer[0].getSaveHandler().getWorldDirectoryName());
     */
    public void deleteWorldAndStopServer() {
        this.worldIsBeingDeleted = true;
        this.getActiveAnvilConverter().flushCache();

        for (WorldServer var2 : this.worldServers) {
            if (var2 != null) {
                var2.flush();
            }
        }

        this.getActiveAnvilConverter().deleteWorldDirectory(this.worldServers[0].getSaveHandler().getWorldDirectoryName());
        this.initiateShutdown();
    }

    public String getTexturePack() {
        return this.texturePack;
    }

    public void setTexturePack(String par1Str) {
        this.texturePack = par1Str;
    }

    @Override
    public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("whitelist_enabled", false);
        par1PlayerUsageSnooper.addData("whitelist_count", 0);
        par1PlayerUsageSnooper.addData("players_current", this.getCurrentPlayerCount());
        par1PlayerUsageSnooper.addData("players_max", this.getMaxPlayers());
        par1PlayerUsageSnooper.addData("players_seen", this.serverConfigManager.getAvailablePlayerDat().length);
        par1PlayerUsageSnooper.addData("uses_auth", this.onlineMode);
        par1PlayerUsageSnooper.addData("run_time", (getCurrentMillis() - par1PlayerUsageSnooper.func_130105_g()) / 60L * 1000L);
        par1PlayerUsageSnooper.addData("avg_tick_ms", (int) (MathHelper.average(this.tickTimeArray) * 1.0E-6D));
        par1PlayerUsageSnooper.addData("avg_sent_packet_count", (int) MathHelper.average(this.sentPacketCountArray));
        par1PlayerUsageSnooper.addData("avg_sent_packet_size", (int) MathHelper.average(this.sentPacketSizeArray));
        par1PlayerUsageSnooper.addData("avg_rec_packet_count", (int) MathHelper.average(this.receivedPacketCountArray));
        par1PlayerUsageSnooper.addData("avg_rec_packet_size", (int) MathHelper.average(this.receivedPacketSizeArray));
        int var2 = 0;

        for (WorldServer worldServer : this.worldServers) {
            if (worldServer != null) {
                WorldInfo var5 = worldServer.getWorldInfo();
                par1PlayerUsageSnooper.addData("world[" + var2 + "][dimension]", worldServer.provider.dimensionId);
                par1PlayerUsageSnooper.addData("world[" + var2 + "][mode]", var5.getGameType());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][difficulty]", worldServer.difficultySetting);
                par1PlayerUsageSnooper.addData("world[" + var2 + "][hardcore]", var5.isHardcoreModeEnabled());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][generator_name]", var5.getTerrainType().getWorldTypeName());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][generator_version]", var5.getTerrainType().getGeneratorVersion());
                par1PlayerUsageSnooper.addData("world[" + var2 + "][height]", this.buildLimit);
                par1PlayerUsageSnooper.addData("world[" + var2 + "][chunks_loaded]", worldServer.getChunkProvider().getLoadedChunkCount());
                ++var2;
            }
        }

        par1PlayerUsageSnooper.addData("worlds", var2);
    }

    @Override
    public void addServerTypeToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("server_brand", this.getServerModName());
        par1PlayerUsageSnooper.addData("dedicated", this.isDedicatedServer());
    }

    /**
     * Returns whether snooping is enabled or not.
     */
    @Override
    public boolean isSnooperEnabled() {
        // Disables Snooper
        return false;
    }

    /**
     * This is checked to be 16 upon receiving the packet, otherwise the packet is ignored.
     */
    public int textureSize() {
        return 16;
    }

    public abstract boolean isDedicatedServer();

    public boolean isServerInOnlineMode() {
        return this.onlineMode;
    }

    public void setOnlineMode(boolean par1) {
        this.onlineMode = par1;
    }

    public boolean getCanSpawnAnimals() {
        return this.canSpawnAnimals;
    }

    public void setCanSpawnAnimals(boolean par1) {
        this.canSpawnAnimals = par1;
    }

    public boolean getCanSpawnNPCs() {
        return this.canSpawnNPCs;
    }

    public void setCanSpawnNPCs(boolean par1) {
        this.canSpawnNPCs = par1;
    }

    public boolean isPVPEnabled() {
        return this.pvpEnabled;
    }

    public void setAllowPvp(boolean par1) {
        this.pvpEnabled = par1;
    }

    public boolean isFlightAllowed() {
        return this.allowFlight;
    }

    public void setAllowFlight(boolean par1) {
        this.allowFlight = par1;
    }

    /**
     * Return whether command blocks are enabled.
     */
    public abstract boolean isCommandBlockEnabled();

    public String getMOTD() {
        return this.motd;
    }

    public void setMOTD(String par1Str) {
        this.motd = par1Str;
    }

    public int getBuildLimit() {
        return this.buildLimit;
    }

    public void setBuildLimit(int par1) {
        this.buildLimit = par1;
    }

    public boolean isServerStopped() {
        return this.serverStopped;
    }

    public ServerConfigurationManager getConfigurationManager() {
        return this.serverConfigManager;
    }

    public void setConfigurationManager(ServerConfigurationManager par1ServerConfigurationManager) {
        this.serverConfigManager = par1ServerConfigurationManager;
    }

    /**
     * Sets the game type for all worlds.
     */
    public void setGameType(EnumGameType par1EnumGameType) {
        for (int var2 = 0; var2 < this.worldServers.length; ++var2) {
            getServer().worldServers[var2].getWorldInfo().setGameType(par1EnumGameType);
        }
    }

    public abstract NetworkListenThread getNetworkThread();

    public int getTickCounter() {
        return this.tickCounter;
    }

    public void enableProfiling() {
        this.startProfiling = true;
    }

    /**
     * Return the position for this command sender.
     */
    @Override
    public ChunkCoordinates getCommandSenderPosition() {
        return new ChunkCoordinates(0, 0, 0);
    }

    @Override
    public World getOverworld() {
        return this.worldServers[0];
    }

    /**
     * Return the spawn protection area's size.
     */
    public int getSpawnProtectionSize() {
        return 16;
    }

    public boolean func_96290_a(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
        return false;
    }

    @Override
    public abstract ILogAgent getLogAgent();

    public void func_104055_i(boolean par1) {
        this.field_104057_T = par1;
    }

    public boolean func_104056_am() {
        return this.field_104057_T;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public static long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    /**
     * Gets the current player count, maximum player count, and player entity list.
     */
    public static ServerConfigurationManager getServerConfigurationManager(MinecraftServer par0MinecraftServer) {
        return par0MinecraftServer.serverConfigManager;
    }

    public void minetweakInfo(String line) {
        Minetweak.getLogger().info(line);
    }
}