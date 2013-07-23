package net.minecraft.server;

import net.minecraft.utils.callable.CallableServerType;
import net.minecraft.utils.callable.CallableType;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.EntityPlayer;
import net.minecraft.utils.enums.EnumGameType;
import net.minecraft.logging.ILogAgent;
import net.minecraft.logging.LogAgent;
import net.minecraft.server.network.NetworkListenThread;
import net.minecraft.player.PlayerUsageSnooper;
import net.minecraft.server.rcon.RConThreadMain;
import net.minecraft.server.rcon.RConThreadQuery;
import net.minecraft.src.*;
import net.minecraft.utils.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.ChunkCoordinates;
import org.minetweak.Minetweak;
import org.minetweak.Server;
import org.minetweak.command.Console;
import org.minetweak.config.MinetweakConfig;
import org.minetweak.config.Property;
import org.minetweak.event.server.ServerFinishedStartupEvent;
import org.minetweak.event.server.ServerInitializedEvent;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("FieldCanBeLocal")
public class DedicatedServer extends MinecraftServer implements IServer {
    private final List pendingCommandList = Collections.synchronizedList(new ArrayList());
    private final ILogAgent field_98131_l;
    private RConThreadQuery theRConThreadQuery;
    private RConThreadMain theRConThreadMain;
    private PropertyManager settings;
    private boolean canSpawnStructures;
    private EnumGameType gameType;
    private NetworkListenThread networkThread;

    public DedicatedServer(File par1File) {
        super(par1File);
        this.field_98131_l = new LogAgent("Minecraft-Server", null, (new File(par1File, "server.log")).getAbsolutePath());
        new DedicatedServerSleepThread(this);
    }

    /**
     * Initialises the server and starts it.
     */
    protected boolean startServer() throws IOException {
        DedicatedServerCommandThread var1 = new DedicatedServerCommandThread(this);
        var1.setDaemon(true);
        var1.start();
        this.logInfo("Starting Minecraft server version 1.6.2");

        this.minetweakInfo("Loading Configuration");
        this.setOnlineMode(MinetweakConfig.getBoolean("server.online", true));
        this.setHostname(MinetweakConfig.get("server.ip", ""));
        this.setCanSpawnAnimals(MinetweakConfig.getBoolean("server.spawn-animals", true));
        this.setCanSpawnNPCs(MinetweakConfig.getBoolean("server.spawn-npcs", true));
        this.setAllowPvp(MinetweakConfig.getBoolean("server.pvp", true));
        this.setAllowFlight(MinetweakConfig.getBoolean("server.allow-flight", false));
        this.setTexturePack(MinetweakConfig.get("server.texture-pack", ""));
        this.setMOTD(MinetweakConfig.get("server.motd", "A Minetweak Server"));

        MinetweakConfig.getBoolean("server.enable-command-block", true);

        this.func_104055_i(MinetweakConfig.getBoolean("server.force-gamemode", false));

        if (MinetweakConfig.getInteger("server.difficulty", 1) < 0) {
            MinetweakConfig.set("server.difficulty", ((Integer) 0).toString());
        } else if (MinetweakConfig.getInteger("server.difficulty", 1) > 3) {
            MinetweakConfig.getInteger("server.difficulty", 3);
        }

        this.canSpawnStructures = MinetweakConfig.getBoolean("server.generate-structures", true);
        int var2 = MinetweakConfig.getInteger("server.gamemode", EnumGameType.SURVIVAL.getID());
        this.gameType = WorldSettings.getGameTypeById(var2);
        this.logInfo("Default game type: " + this.gameType);
        InetAddress var3 = null;

        if (this.getServerHostname().length() > 0) {
            var3 = InetAddress.getByName(this.getServerHostname());
        }

        if (this.getServerPort() < 0) {
            this.setServerPort(MinetweakConfig.getInteger("server.port", 25565));
        }

        this.setKeyPair(CryptManager.generateKeyPair());
        this.logInfo("Starting Minecraft server on " + (this.getServerHostname().length() == 0 ? "*" : this.getServerHostname()) + ":" + this.getServerPort());

        Minetweak.getEventBus().post(new ServerInitializedEvent());

        try {
            this.networkThread = new DedicatedServerListenThread(this, var3, this.getServerPort());
        } catch (IOException var16) {
            this.getLogAgent().logSevere("**** FAILED TO BIND TO PORT!");
            this.getLogAgent().logWarningFormatted("The exception was: {0}", var16.toString());
            this.getLogAgent().logSevere("Perhaps a server is already running on that port?");
            return false;
        }

        if (!this.isServerInOnlineMode()) {
            this.getLogAgent().logWarning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            this.getLogAgent().logWarning("The server will make no attempt to authenticate usernames. Beware.");
            this.getLogAgent().logWarning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            this.getLogAgent().logWarning("To change this, set \"online-mode\" to \"true\" in the minetweak.cfg file.");
        }

        this.setConfigurationManager(new DedicatedPlayerList(this));
        long var4 = System.nanoTime();

        if (this.getFolderName() == null) {
            this.setFolderName(MinetweakConfig.get("server.level-name", "world"));
        }

        String var6 = MinetweakConfig.get("server.level-seed");
        String var7 = MinetweakConfig.get("server.level-type", "DEFAULT");
        String var8 = MinetweakConfig.get("server.generator-settings", "");
        long var9 = (new Random()).nextLong();

        if (var6.length() > 0) {
            try {
                long var11 = Long.parseLong(var6);

                if (var11 != 0L) {
                    var9 = var11;
                }
            } catch (NumberFormatException var15) {
                var9 = (long) var6.hashCode();
            }
        }

        WorldType var17 = WorldType.parseWorldType(var7);

        if (var17 == null) {
            var17 = WorldType.DEFAULT;
        }

        this.setBuildLimit(MinetweakConfig.getInteger("server.max-build-height", 256));
        this.setBuildLimit((this.getBuildLimit() + 8) / 16 * 16);
        this.setBuildLimit(MathHelper.clamp_int(this.getBuildLimit(), 64, 256));
        MinetweakConfig.set("server.max-build-height", "" + this.getBuildLimit());
        this.logInfo("Preparing level \"" + this.getFolderName() + "\"");
        this.loadAllWorlds(this.getFolderName(), this.getFolderName(), var9, var17, var8);
        long var12 = System.nanoTime() - var4;
        String var14 = String.format("%.3fs", (double) var12 / 1.0E9D);

        Minetweak.getEventBus().post(new ServerFinishedStartupEvent());

        this.logInfo("Done (" + var14 + ")! For help, type help");

        Minetweak.setServerDoneLoading();

        if (MinetweakConfig.getBoolean("server.enable-query", false)) {
            this.logInfo("Starting GS4 status listener");
            this.theRConThreadQuery = new RConThreadQuery(this);
            this.theRConThreadQuery.startThread();
        }

        if (MinetweakConfig.getBoolean("server.enable-rcon", false)) {
            this.logInfo("Starting remote control listener");
            this.theRConThreadMain = new RConThreadMain(this);
            this.theRConThreadMain.startThread();
        }

        return true;
    }

    public boolean canStructuresSpawn() {
        return this.canSpawnStructures;
    }

    public EnumGameType getGameType() {
        return this.gameType;
    }

    /**
     * Defaults to "1" (Easy) for the dedicated server, defaults to "2" (Normal) on the client.
     */
    public int getDifficulty() {
        return MinetweakConfig.getInteger("server.difficulty");
    }

    /**
     * Defaults to false.
     */
    public boolean isHardcore() {
        return MinetweakConfig.getBoolean("server.hardcore", false);
    }

    /**
     * Called on exit from the main run() loop.
     */
    protected void finalTick(CrashReport par1CrashReport) {
        while (this.isServerRunning()) {
            this.executePendingCommands();

            try {
                Thread.sleep(10L);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        }
    }

    /**
     * Adds the server info, including from theWorldServer, to the crash report.
     */
    public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport) {
        par1CrashReport = super.addServerInfoToCrashReport(par1CrashReport);
        par1CrashReport.func_85056_g().addCrashSectionCallable("Is Modded", new CallableType(this));
        par1CrashReport.func_85056_g().addCrashSectionCallable("Type", new CallableServerType(this));
        return par1CrashReport;
    }

    /**
     * Directly calls System.exit(0), instantly killing the program.
     */
    protected void systemExitNow() {
        System.exit(0);
    }

    public void updateTimeLightAndEntities() {
        super.updateTimeLightAndEntities();
        this.executePendingCommands();
    }

    public boolean getAllowNether() {
        return MinetweakConfig.getBoolean("server.allow-nether", true);
    }

    public boolean allowSpawnMonsters() {
        return MinetweakConfig.getBoolean("server.spawn-monsters", true);
    }

    public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("whitelist_enabled", this.getDedicatedPlayerList().isWhiteListEnabled());
        par1PlayerUsageSnooper.addData("whitelist_count", this.getDedicatedPlayerList().getWhiteListedPlayers().size());
        super.addServerStatsToSnooper(par1PlayerUsageSnooper);
    }

    /**
     * Returns whether snooping is enabled or not.
     */
    public boolean isSnooperEnabled() {
        return MinetweakConfig.getBoolean("server.snooper-enabled", true);
    }

    public void addPendingCommand(String par1Str, ICommandSender par2ICommandSender) {
        Console console = new Console();
        if (Minetweak.doesCommandExist(par1Str.split(" ")[0])) {
            Server.handleCommand(console, par1Str);
        } else {
            console.sendMessage("That command doesn't exist. Type help for help.");
        }
        //this.pendingCommandList.add(new ServerCommand(par1Str, par2ICommandSender));
    }

    public void executePendingCommands() {
        while (!this.pendingCommandList.isEmpty()) {
            ServerCommand var1 = (ServerCommand) this.pendingCommandList.remove(0);
            this.getCommandManager().executeCommand(var1.sender, var1.command);
        }
    }

    public boolean isDedicatedServer() {
        return true;
    }

    public DedicatedPlayerList getDedicatedPlayerList() {
        return (DedicatedPlayerList) super.getConfigurationManager();
    }

    public NetworkListenThread getNetworkThread() {
        return this.networkThread;
    }

    /**
     * Gets an integer property. If it does not exist, set it to the specified value.
     */
    public int getIntProperty(String par1Str, int par2) {
        return Integer.parseInt(MinetweakConfig.get(new Property(par1Str, "" + par2)));
    }

    /**
     * Gets a string property. If it does not exist, set it to the specified value.
     */
    public String getStringProperty(String par1Str, String par2Str) {
        return MinetweakConfig.get(par1Str, par2Str);
    }

    /**
     * Gets a boolean property. If it does not exist, set it to the specified value.
     */
    public boolean getBooleanProperty(String par1Str, boolean par2) {
        return MinetweakConfig.get(new Property(par1Str, Boolean.toString(par2))).equals("true");
    }

    /**
     * Saves an Object with the given property name.
     */
    public void setProperty(String par1Str, Object par2Obj) {
        MinetweakConfig.set(par1Str, "" + par2Obj);
    }

    /**
     * Saves all of the server properties to the properties file.
     */
    public void saveProperties() {
        this.settings.saveProperties();
    }

    /**
     * Returns the filename where server properties are stored
     */
    public String getSettingsFilename() {
        File var1 = MinetweakConfig.getConfigFile();
        return var1 != null ? var1.getAbsolutePath() : "No settings file";
    }

    /**
     * Return whether command blocks are enabled.
     */
    public boolean isCommandBlockEnabled() {
        return MinetweakConfig.getBoolean("server.enable-command-block");
    }

    /**
     * Return the spawn protection area's size.
     */
    public int getSpawnProtectionSize() {
        return MinetweakConfig.getInteger("server.spawn-protection");
    }

    public boolean func_96290_a(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
        if (par1World.provider.dimensionId != 0) {
            return false;
        } else if (this.getDedicatedPlayerList().getOps().isEmpty()) {
            return false;
        } else if (this.getDedicatedPlayerList().areCommandsAllowed(par5EntityPlayer.getCommandSenderName())) {
            return false;
        } else if (this.getSpawnProtectionSize() <= 0) {
            return false;
        } else {
            ChunkCoordinates var6 = par1World.getSpawnPoint();
            int var7 = MathHelper.abs_int(par2 - var6.posX);
            int var8 = MathHelper.abs_int(par4 - var6.posZ);
            int var9 = Math.max(var7, var8);
            return var9 <= this.getSpawnProtectionSize();
        }
    }

    public ILogAgent getLogAgent() {
        return this.field_98131_l;
    }

    public int func_110455_j() {
        return MinetweakConfig.getInteger("server.op-permission-level", 4);
    }

    public ServerConfigurationManager getConfigurationManager() {
        return this.getDedicatedPlayerList();
    }
}
