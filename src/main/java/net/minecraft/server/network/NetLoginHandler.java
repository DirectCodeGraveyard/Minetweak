package net.minecraft.server.network;

import net.minecraft.entity.EntityPlayerMP;
import net.minecraft.server.DedicatedServerListenThread;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerConfigurationManager;
import net.minecraft.server.network.packet.*;
import net.minecraft.server.network.tcp.TcpConnection;
import net.minecraft.src.ThreadLoginVerifier;
import net.minecraft.utils.StringUtils;
import org.minetweak.Minetweak;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class NetLoginHandler extends NetHandler {
    /**
     * The Random object used to generate serverId hex strings.
     */
    private static Random random = new Random();

    /**
     * The 4 byte verify token read from a Packet252SharedKey
     */
    private byte[] verifyToken;

    /**
     * Reference to the MinecraftServer object.
     */
    private final MinecraftServer mcServer;
    public final TcpConnection connection;

    /**
     * Returns if the login handler is finished and can be removed. It is set to true on either error or successful
     * login.
     */
    public boolean finishedProcessing;

    /**
     * While waiting to login, if this field ++'s to 600 it will kick you.
     */
    private int loginTimer;
    private String clientUsername;
    private volatile boolean field_72544_i;

    /**
     * server ID that is randomly generated by this login handler.
     */
    private String loginServerId = "";
    private boolean isOfflineModeEnabled;

    /**
     * Secret AES key obtained from the client's Packet252SharedKey
     */
    private SecretKey sharedKey;

    public NetLoginHandler(MinecraftServer par1MinecraftServer, Socket par2Socket, String par3Str) throws IOException {
        this.mcServer = par1MinecraftServer;
        this.connection = new TcpConnection(par1MinecraftServer.getLogAgent(), par2Socket, par3Str, this, par1MinecraftServer.getKeyPair().getPrivate());
        this.connection.field_74468_e = 0;
    }

    /**
     * Logs the user in if a login packet is found, otherwise keeps processing network packets unless the timeout has
     * occurred.
     */
    public void tryLogin() {
        if (this.field_72544_i) {
            this.initializePlayerConnection();
        }

        if (this.loginTimer++ == 600) {
            this.kickUser("Took too long to log in");
        } else {
            this.connection.processReadPackets();
        }
    }

    /**
     * Disconnects the user with the given reason.
     */
    public void kickUser(String par1Str) {
        try {
            this.mcServer.logInfo("Disconnecting " + this.getUsernameAndAddress() + ": " + par1Str);
            this.connection.addToSendQueue(new Packet255KickDisconnect(par1Str));
            this.connection.serverShutdown();
            this.finishedProcessing = true;
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    @Override
    public void handleClientProtocol(Packet2ClientProtocol par1Packet2ClientProtocol) {
        this.clientUsername = par1Packet2ClientProtocol.getUsername();

        if (!this.clientUsername.equals(StringUtils.stripControlCodes(this.clientUsername))) {
            this.kickUser("Invalid username!");
        } else {
            PublicKey var2 = this.mcServer.getKeyPair().getPublic();

            if (par1Packet2ClientProtocol.getProtocolVersion() != Minetweak.getProtocolVersion()) {
                if (par1Packet2ClientProtocol.getProtocolVersion() > Minetweak.getProtocolVersion()) {
                    this.kickUser("Outdated server! Mismatched Protocol Version \'" + par1Packet2ClientProtocol.getProtocolVersion() + "'");
                } else {
                    this.kickUser("Outdated client!");
                }
            } else {
                this.loginServerId = this.mcServer.isServerInOnlineMode() ? Long.toString(random.nextLong(), 16) : "-";
                this.verifyToken = new byte[4];
                random.nextBytes(this.verifyToken);
                this.connection.addToSendQueue(new Packet253ServerAuthData(this.loginServerId, var2, this.verifyToken));
            }
        }
    }

    @Override
    public void handleSharedKey(Packet252SharedKey par1Packet252SharedKey) {
        PrivateKey var2 = this.mcServer.getKeyPair().getPrivate();
        this.sharedKey = par1Packet252SharedKey.getSharedKey(var2);

        if (!Arrays.equals(this.verifyToken, par1Packet252SharedKey.getVerifyToken(var2))) {
            this.kickUser("Invalid client reply");
        }

        this.connection.addToSendQueue(new Packet252SharedKey());
    }

    @Override
    public void handleClientCommand(Packet205ClientCommand par1Packet205ClientCommand) {
        if (par1Packet205ClientCommand.forceRespawn == 0) {
            if (this.isOfflineModeEnabled) {
                this.kickUser("Duplicate login");
                return;
            }

            this.isOfflineModeEnabled = true;

            if (this.mcServer.isServerInOnlineMode()) {
                (new ThreadLoginVerifier(this)).start();
            } else {
                this.field_72544_i = true;
            }
        }
    }

    @Override
    public void handleUpdateAttributes(Packet44UpdateAttributes par1Packet44UpdateAttributes) {
    }

    @Override
    public void handleTileEditorOpen(Packet133TileEditorOpen par1Packet133TileEditorOpen) {
    }

    @Override
    public void handleLogin(Packet1Login par1Packet1Login) {
    }

    /**
     * on success the specified username is connected to the minecraftInstance, otherwise they are packet255'd
     */
    public void initializePlayerConnection() {
        String var1 = this.mcServer.getConfigurationManager().allowUserToConnect(this.connection.getRemoteAddress(), this.clientUsername);

        if (var1 != null) {
            this.kickUser(var1);
        } else {
            EntityPlayerMP var2 = this.mcServer.getConfigurationManager().createPlayerForUser(this.clientUsername);

            if (var2 != null) {
                this.mcServer.getConfigurationManager().initializeConnectionToPlayer(this.connection, var2);
            }
        }

        this.finishedProcessing = true;
    }

    @Override
    public void handleErrorMessage(String par1Str, Object[] par2ArrayOfObj) {
        this.mcServer.logInfo(this.getUsernameAndAddress() + " lost connection");
        this.finishedProcessing = true;
    }

    /**
     * Handle a server ping packet.
     */
    @Override
    public void handleServerPing(Packet254ServerPing par1Packet254ServerPing) {
        // Minetweak.info("Received Server Query from Client using address " + par1Packet254ServerPing.field_140052_b + ":" + par1Packet254ServerPing.field_140053_c);
        try {
            ServerConfigurationManager configManager = this.mcServer.getConfigurationManager();
            String var3 = null;

            if (par1Packet254ServerPing.func_140050_d()) {
                var3 = this.mcServer.getMOTD() + "\u00a7" + configManager.getCurrentPlayerCount() + "\u00a7" + configManager.getMaxPlayers();
            } else {
                List var4 = Arrays.asList(1, Minetweak.getProtocolVersion(), this.mcServer.getMinecraftVersion(), this.mcServer.getMOTD(), configManager.getCurrentPlayerCount(), configManager.getMaxPlayers());
                Object var6;

                for (Iterator var5 = var4.iterator(); var5.hasNext(); var3 = var3 + var6.toString().replaceAll("\u0000", "")) {
                    var6 = var5.next();

                    if (var3 == null) {
                        var3 = "\u00a7";
                    } else {
                        var3 = var3 + "\u0000";
                    }
                }
            }

            InetAddress var8 = null;

            if (this.connection.getSocket() != null) {
                var8 = this.connection.getSocket().getInetAddress();
            }

            this.connection.addToSendQueue(new Packet255KickDisconnect(var3));
            this.connection.serverShutdown();

            if (var8 != null && this.mcServer.getNetworkThread() instanceof DedicatedServerListenThread) {
                ((DedicatedServerListenThread) this.mcServer.getNetworkThread()).func_71761_a(var8);
            }

            this.finishedProcessing = true;
        } catch (Exception var7) {
            var7.printStackTrace();
        }
    }

    /**
     * Default handler called for packets that don't have their own handlers in NetServerHandler; kicks player from the
     * server.
     */
    @Override
    public void unexpectedPacket(Packet par1Packet) {
        this.kickUser("Protocol error");
    }

    public String getUsernameAndAddress() {
        return this.clientUsername != null ? this.clientUsername + " [" + this.connection.getRemoteAddress().toString() + "]" : this.connection.getRemoteAddress().toString();
    }

    /**
     * determine if it is a server handler
     */
    @Override
    public boolean isServerHandler() {
        return true;
    }

    /**
     * Returns the server Id randomly generated by this login handler.
     */
    public static String getServerId(NetLoginHandler par0NetLoginHandler) {
        return par0NetLoginHandler.loginServerId;
    }

    /**
     * Returns the reference to Minecraft Server.
     */
    public static MinecraftServer getLoginMinecraftServer(NetLoginHandler par0NetLoginHandler) {
        return par0NetLoginHandler.mcServer;
    }

    /**
     * Return the secret AES sharedKey
     */
    public static SecretKey getSharedKey(NetLoginHandler par0NetLoginHandler) {
        return par0NetLoginHandler.sharedKey;
    }

    /**
     * Returns the connecting client username.
     */
    public static String getClientUsername(NetLoginHandler par0NetLoginHandler) {
        return par0NetLoginHandler.clientUsername;
    }

    public static boolean func_72531_a(NetLoginHandler par0NetLoginHandler, boolean par1) {
        return par0NetLoginHandler.field_72544_i = par1;
    }
}
