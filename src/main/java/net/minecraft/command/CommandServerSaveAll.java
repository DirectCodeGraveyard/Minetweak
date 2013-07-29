package net.minecraft.command;

import net.minecraft.crash.exception.MinecraftException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.utils.chat.ChatMessageComponent;
import net.minecraft.world.WorldServer;

public class CommandServerSaveAll extends CommandBase {
    public String getCommandName() {
        return "save-all";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel() {
        return 4;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.save.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        MinecraftServer var3 = MinecraftServer.getServer();
        par1ICommandSender.func_110122_a(ChatMessageComponent.createPremade("commands.save.start"));

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

            if (par2ArrayOfStr.length > 0 && "flush".equals(par2ArrayOfStr[0])) {
                par1ICommandSender.func_110122_a(ChatMessageComponent.createPremade("commands.save.flushStart"));

                for (var4 = 0; var4 < var3.worldServers.length; ++var4) {
                    if (var3.worldServers[var4] != null) {
                        var5 = var3.worldServers[var4];
                        var6 = var5.levelSaving;
                        var5.levelSaving = false;
                        var5.func_104140_m();
                        var5.levelSaving = var6;
                    }
                }

                par1ICommandSender.func_110122_a(ChatMessageComponent.createPremade("commands.save.flushEnd"));
            }
        } catch (MinecraftException var7) {
            notifyAdmins(par1ICommandSender, "commands.save.failed", var7.getMessage());
            return;
        }

        notifyAdmins(par1ICommandSender, "commands.save.success", new Object[0]);
    }
}
