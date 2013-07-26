package net.minecraft.server.rcon;

import net.minecraft.server.IServer;
import org.minetweak.config.MinetweakConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RConThreadMain extends RConThreadBase {
    /**
     * Port RCon is running on
     */
    private int rconPort;

    /**
     * Hostname RCon is running on
     */
    private String hostname;

    /**
     * The RCon ServerSocket.
     */
    private ServerSocket serverSocket;

    /**
     * The RCon password
     */
    private String rconPassword;

    /**
     * A map of client addresses to their running Threads
     */
    private Map<Object, RConThreadClient> clientThreads;

    public RConThreadMain(IServer par1IServer) {
        super(par1IServer);
        this.rconPort = par1IServer.getIntProperty("rcon.port", 0);
        this.rconPassword = par1IServer.getStringProperty("rcon.password", "");
        this.hostname = par1IServer.getHostname();
        /*
      Port the server is running on
     */
        int serverPort = par1IServer.getPort();

        if (0 == this.rconPort) {
            this.rconPort = serverPort + 10;
            this.logInfo("Setting default rcon port to " + this.rconPort);
            MinetweakConfig.set("rcon.port", "" + this.rconPort);

            if (0 == this.rconPassword.length()) {
                MinetweakConfig.set("rcon.password", "");
            }
        }

        if (0 == this.hostname.length()) {
            this.hostname = "0.0.0.0";
        }

        this.initClientThreadList();
        this.serverSocket = null;
    }

    private void initClientThreadList() {
        this.clientThreads = new HashMap<Object, RConThreadClient>();
    }

    /**
     * Cleans up the clientThreads map by removing client Threads that are not running
     */
    private void cleanClientThreadsMap() {
        Iterator<Entry<Object, RConThreadClient>> var1 = this.clientThreads.entrySet().iterator();

        while (var1.hasNext()) {
            Entry<Object, RConThreadClient> var2 = var1.next();

            if (!(var2.getValue()).isRunning()) {
                var1.remove();
            }
        }
    }

    @Override
    public void run() {
        this.logInfo("RCON running on " + this.hostname + ":" + this.rconPort);

        try {
            while (this.running) {
                try {
                    Socket var1 = this.serverSocket.accept();
                    var1.setSoTimeout(500);
                    RConThreadClient var2 = new RConThreadClient(this.server, var1);
                    var2.startThread();
                    this.clientThreads.put(var1.getRemoteSocketAddress(), var2);
                    this.cleanClientThreadsMap();
                } catch (SocketTimeoutException var7) {
                    this.cleanClientThreadsMap();
                } catch (IOException var8) {
                    this.logInfo("IO: " + var8.getMessage());
                }
            }
        } finally {
            this.closeServerSocket(this.serverSocket);
        }
    }

    /**
     * Creates a new Thread object from this class and starts running
     */
    @Override
    public void startThread() {
        if (0 == this.rconPassword.length()) {
            this.logWarning("No rcon password set in \'" + this.server.getSettingsFilename() + "\', rcon disabled!");
        } else if (0 < this.rconPort && 65535 >= this.rconPort) {
            if (!this.running) {
                try {
                    this.serverSocket = new ServerSocket(this.rconPort, 0, InetAddress.getByName(this.hostname));
                    this.serverSocket.setSoTimeout(500);
                    super.startThread();
                } catch (IOException var2) {
                    this.logWarning("Unable to initialise rcon on " + this.hostname + ":" + this.rconPort + " : " + var2.getMessage());
                }
            }
        } else {
            this.logWarning("Invalid rcon port " + this.rconPort + " found in \'" + this.server.getSettingsFilename() + "\', rcon disabled!");
        }
    }
}
