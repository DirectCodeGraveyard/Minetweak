package org.minetweak.command;

import net.minecraft.crash.exception.MinecraftException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import org.minetweak.server.Server;

public class CommandSave extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        Server.broadcastMessage("Starting World Save...");
        MinecraftServer var3 = MinecraftServer.getServer();

        if (var3.getConfigurationManager() != null) {
            var3.getConfigurationManager().saveAllPlayerData();
        }

        try {
            int var4;
            WorldServer var5;
            boolean var6;

            for (var4 = 0; var4 < var3.worldServers.length; ++var4) {
                if (var3.worldServers[var4] != null) {
                    var5 = var3.worldServers[var4];
                    var6 = var5.levelSaving;
                    var5.levelSaving = false;
                    var5.saveAllChunks(true, null);
                    var5.levelSaving = var6;
                }
            }

            for (var4 = 0; var4 < var3.worldServers.length; ++var4) {
                if (var3.worldServers[var4] != null) {
                    var5 = var3.worldServers[var4];
                    var6 = var5.levelSaving;
                    var5.levelSaving = false;
                    var5.func_104140_m();
                    var5.levelSaving = var6;
                }
            }
            Server.broadcastMessage("World Save Complete.");
        } catch (MinecraftException var7) {
            Server.broadcastMessage("World Save Failed.");
        }
    }

    @Override
    public String getHelpInfo() {
        return "Saves Server Data";
    }
}
