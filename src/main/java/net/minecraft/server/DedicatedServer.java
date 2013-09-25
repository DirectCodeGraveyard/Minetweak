package net.minecraft.server;

import net.minecraft.crash.CrashReport;
import net.minecraft.entity.EntityPlayer;
import net.minecraft.logging.ILogAgent;
import net.minecraft.logging.LogAgent;
import net.minecraft.player.PlayerUsageSnooper;
import net.minecraft.server.network.NetworkListenThread;
import net.minecraft.server.rcon.RConThreadMain;
import net.minecraft.server.rcon.RConThreadQuery;
import net.minecraft.src.CryptManager;
import net.minecraft.utils.MathHelper;
import net.minecraft.utils.callable.CallableServerType;
import net.minecraft.utils.callable.CallableType;
import net.minecraft.utils.enums.EnumGameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.ChunkCoordinates;
import org.minetweak.Minetweak;
import org.minetweak.config.GameConfig;
import org.minetweak.config.Property;
import org.minetweak.console.Console;
import org.minetweak.event.server.ServerFinishedStartupEvent;
import org.minetweak.event.server.ServerInitializedEvent;
import org.minetweak.server.Server;

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
    private final ILogAgent logAgent;
    private RConThreadQuery theRConThreadQuery;
    private RConThreadMain theRConThreadMain;
    private boolean canSpawnStructures;
    private EnumGameType gameType;
    private NetworkListenThread networkThread;

    public DedicatedServer(File par1File) {
        super(par1File);
        this.logAgent = new LogAgent("Minecraft-Server", null);
        new DedicatedServerSleepThread(this);
    }

    /**
     * Initialises the server and starts it.
     */
    @Override
    protected boolean startServer() throws IOException {
        DedicatedServerCommandThread var1 = new DedicatedServerCommandThread(this);
        var1.setDaemon(true);
        var1.start();

        this.setOnlineMode(GameConfig.getBoolean("server.online", true));
        this.setHostname(GameConfig.get("server.ip", ""));
        this.setCanSpawnAnimals(GameConfig.getBoolean("server.spawn-animals", true));
        this.setCanSpawnNPCs(GameConfig.getBoolean("server.spawn-npcs", true));
        this.setAllowPvp(GameConfig.getBoolean("server.pvp", true));
        this.setAllowFlight(GameConfig.getBoolean("server.allow-flight", false));
        this.setTexturePack(GameConfig.get("server.texture-pack", ""));
        this.setMOTD(GameConfig.get("server.motd", "A Minetweak Server"));

        GameConfig.getBoolean("server.enable-command-block", true);

        this.func_104055_i(GameConfig.getBoolean("server.force-gamemode", false));

        if (GameConfig.getInteger("server.difficulty", 1) < 0) {
            GameConfig.set("server.difficulty", ((Integer) 0).toString());
        } else if (GameConfig.getInteger("server.difficulty", 1) > 3) {
            GameConfig.getInteger("server.difficulty", 3);
        }

        this.canSpawnStructures = GameConfig.getBoolean("server.generate-structures", true);
        int var2 = GameConfig.getInteger("server.gamemode", EnumGameType.SURVIVAL.getID());
        this.gameType = WorldSettings.getGameTypeById(var2);
        InetAddress var3 = null;

        if (this.getServerHostname().length() > 0) {
            var3 = InetAddress.getByName(this.getServerHostname());
        }

        if (this.getServerPort() < 0) {
            this.setServerPort(GameConfig.getInteger("server.port", 25565));
        }

        this.setKeyPair(CryptManager.generateKeyPair());

        Minetweak.getEventBus().post(new ServerInitializedEvent());

        try {
            this.networkThread = new DedicatedServerListenThread(this, var3, this.getServerPort());
        } catch (IOException var16) {
            this.getLogAgent().logSevere("Error: Failed to bind to port.");
            this.getLogAgent().logSevere("There may be another server running on the same port.");
            return false;
        }

        if (!this.isServerInOnlineMode()) {
            this.getLogAgent().logWarning("Attention: You are running in offline mode.");
            this.getLogAgent().logWarning("This opens up the server to anyone and everyone, using any username, no authentication required.");
        }

        this.setConfigurationManager(new DedicatedPlayerList(this));
        long var4 = System.nanoTime();

        if (this.getFolderName() == null) {
            this.setFolderName(GameConfig.get("server.level-name", "world"));
        }

        String var6 = GameConfig.get("server.level-seed");
        String var7 = GameConfig.get("server.level-type", "DEFAULT");
        String var8 = GameConfig.get("server.generator-settings", "");
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

        this.setBuildLimit(GameConfig.getInteger("server.max-build-height", 256));
        this.setBuildLimit((this.getBuildLimit() + 8) / 16 * 16);
        this.setBuildLimit(MathHelper.clamp_int(this.getBuildLimit(), 64, 256));
        GameConfig.set("server.max-build-height", String.valueOf(this.getBuildLimit()));
        this.loadAllWorlds(this.getFolderName(), this.getFolderName(), var9, var17, var8);
        long var12 = System.nanoTime() - var4;
        String var14 = String.format("%.3fs", (double) var12 / 1.0E9D);

        Minetweak.getEventBus().post(new ServerFinishedStartupEvent());

        Minetweak.setServerDoneLoading();

        this.logInfo("Finished Startup (" + var14 + ")! Type help for a list of commands.");

        if (GameConfig.getBoolean("server.enable-query", false)) {
            this.theRConThreadQuery = new RConThreadQuery(this);
            this.theRConThreadQuery.startThread();
        }

        if (GameConfig.getBoolean("server.enable-rcon", false)) {
            this.theRConThreadMain = new RConThreadMain(this);
            this.theRConThreadMain.startThread();
        }

        return true;
    }

    @Override
    public boolean canStructuresSpawn() {
        return this.canSpawnStructures;
    }

    @Override
    public EnumGameType getGameType() {
        return this.gameType;
    }

    /**
     * Defaults to "1" (Easy) for the dedicated server, defaults to "2" (Normal) on the client.
     */
    @Override
    public int getDifficulty() {
        return GameConfig.getInteger("server.difficulty");
    }

    /**
     * Defaults to false.
     */
    @Override
    public boolean isHardcore() {
        return GameConfig.getBoolean("server.hardcore", false);
    }

    /**
     * Called on exit from the main run() loop.
     */
    @Override
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
    @Override
    public CrashReport addServerInfoToCrashReport(CrashReport par1CrashReport) {
        par1CrashReport = super.addServerInfoToCrashReport(par1CrashReport);
        par1CrashReport.func_85056_g().addCrashSectionCallable("Is Modded", new CallableType(this));
        par1CrashReport.func_85056_g().addCrashSectionCallable("Type", new CallableServerType(this));
        return par1CrashReport;
    }

    @Override
    public void updateTimeLightAndEntities() {
        super.updateTimeLightAndEntities();
        this.executePendingCommands();
    }

    @Override
    public boolean getAllowNether() {
        return GameConfig.getBoolean("server.allow-nether", true);
    }

    @Override
    public boolean allowSpawnMonsters() {
        return GameConfig.getBoolean("server.spawn-monsters", true);
    }

    @Override
    public void addServerStatsToSnooper(PlayerUsageSnooper par1PlayerUsageSnooper) {
        par1PlayerUsageSnooper.addData("whitelist_enabled", this.getDedicatedPlayerList().isWhiteListEnabled());
        par1PlayerUsageSnooper.addData("whitelist_count", this.getDedicatedPlayerList().getWhiteListedPlayers().size());
        super.addServerStatsToSnooper(par1PlayerUsageSnooper);
    }

    /**
     * Returns whether snooping is enabled or not.
     */
    @Override
    public boolean isSnooperEnabled() {
        return GameConfig.getBoolean("server.snooper-enabled", true);
    }

    public void addPendingCommand(String par1Str) {
        Console console = new Console();
        if (Minetweak.doesCommandExist(par1Str.split(" ")[0])) {
            Server.handleCommand(console, par1Str);
        } else {
            console.sendMessage("That command doesn't exist. Type help for help.");
        }
    }

    public void executePendingCommands() {
        while (!this.pendingCommandList.isEmpty()) {
            ServerCommand var1 = (ServerCommand) this.pendingCommandList.remove(0);
            this.getCommandManager().executeCommand(var1.sender, var1.command);
        }
    }

    @Override
    public boolean isDedicatedServer() {
        return true;
    }

    public DedicatedPlayerList getDedicatedPlayerList() {
        return (DedicatedPlayerList) super.getConfigurationManager();
    }

    @Override
    public NetworkListenThread getNetworkThread() {
        return this.networkThread;
    }

    /**
     * Get an integer property. If it doesn't exist, set it to the specified value.
     */
    @Override
    public int getIntProperty(String par1Str, int par2) {
        return Integer.parseInt(GameConfig.get(new Property(par1Str, String.valueOf(par2))));
    }

    /**
     * Get a string property. If it doesn't exist, set it to the specified value.
     */
    @Override
    public String getStringProperty(String par1Str, String par2Str) {
        return GameConfig.get(par1Str, par2Str);
    }

    /**
     * Get a boolean property. If it doesn't exist, set it to the specified value.
     */
    public boolean getBooleanProperty(String par1Str, boolean par2) {
        return GameConfig.get(new Property(par1Str, Boolean.toString(par2))).equals("true");
    }

    /**
     * Save an object with the given property name.
     */
    @Override
    public void setProperty(String par1Str, Object par2Obj) {
        GameConfig.set(par1Str, String.valueOf(par2Obj));
    }

    /**
     * Save all of the server properties to the properties file.
     */
    @Override
    public void saveProperties() {}

    /**
     * Return the filename where server properties are stored.
     */
    @Override
    public String getSettingsFilename() {
        File var1 = GameConfig.getConfigFile();
        return var1 != null ? var1.getAbsolutePath() : "No settings file";
    }

    /**
     * Return whether command blocks are enabled.
     */
    @Override
    public boolean isCommandBlockEnabled() {
        return GameConfig.getBoolean("server.enable-command-block");
    }

    /**
     * Return the spawn protection area's size.
     */
    @Override
    public int getSpawnProtectionSize() {
        return GameConfig.getInteger("server.spawn-protection");
    }

    @Override
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

    /**
     * Get the log agent for the dedicated server.
     * @return Dedicated server logger
     */
    @Override
    public ILogAgent getLogAgent() {
        return this.logAgent;
    }

    @Override
    public int getOpPermissionLevel() {
        return GameConfig.getInteger("server.op-permission-level", 4);
    }

    @Override
    public ServerConfigurationManager getConfigurationManager() {
        return this.getDedicatedPlayerList();
    }
}
