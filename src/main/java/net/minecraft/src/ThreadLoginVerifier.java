package net.minecraft.src;

import net.minecraft.server.network.NetLoginHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;

public class ThreadLoginVerifier extends Thread {
    /**
     * The login handler that spawned this thread.
     */
    final NetLoginHandler loginHandler;

    public ThreadLoginVerifier(NetLoginHandler par1NetLoginHandler) {
        this.loginHandler = par1NetLoginHandler;
    }

    public void run() {
        try {
            String serverIDHash = (new BigInteger(CryptManager.getServerIdHash(NetLoginHandler.getServerId(this.loginHandler), NetLoginHandler.getLoginMinecraftServer(this.loginHandler).getKeyPair().getPublic(), NetLoginHandler.getSharedKey(this.loginHandler)))).toString(16);
            URL url = new URL("http://session.minecraft.net/game/checkserver.jsp?user=" + URLEncoder.encode(NetLoginHandler.getClientUsername(this.loginHandler), "UTF-8") + "&serverId=" + URLEncoder.encode(serverIDHash, "UTF-8"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection(NetLoginHandler.getLoginMinecraftServer(this.loginHandler).func_110454_ao()).getInputStream()));
            String output = reader.readLine();
            reader.close();

            if (!"YES".equals(output)) {
                this.loginHandler.kickUser("Failed to verify username!");
                return;
            }

            NetLoginHandler.func_72531_a(this.loginHandler, true);
        } catch (Exception var5) {
            this.loginHandler.kickUser("Failed to verify username! [internal error " + var5 + "]");
            var5.printStackTrace();
        }
    }
}
