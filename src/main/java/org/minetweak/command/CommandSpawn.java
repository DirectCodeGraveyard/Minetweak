package org.minetweak.command;

import net.minecraft.entity.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.chunk.ChunkCoordinates;
import org.minetweak.Minetweak;
import org.minetweak.chat.ChatFormatting;
import org.minetweak.entity.Player;

public class CommandSpawn extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        EntityPlayerMP playerMP;
        if (sender.hasPermission("minetweak.command.spawn")) {
            if (args.length > 1) {
                sender.sendMessage("Usage: /" + overallCommand + " [player]");
                return;
            }
            if (sender instanceof Console) {
                sender.sendMessage("Console has no need to teleport to spawn.");
                return;
            }
            String playerName = sender.getName();
            if (args.length == 1) {
                playerName = args[0];
            }
            Player player = Minetweak.getPlayerByName(playerName);
            if (player == null) {
                sender.sendMessage(ChatFormatting.RED + "Can't teleport an offline player.");
                return;
            }
            playerMP = player.getPlayerMP();
        } else {
            noPermission(sender, "teleport players to spawn");
            return;
        }
        playerMP.addChatMessage(ChatFormatting.GOLD + "Teleporting to Spawn");
        ChunkCoordinates position = MinecraftServer.getServer().worldServers[playerMP.dimension].getSpawnPoint();
        playerMP.playerNetServerHandler.setPlayerLocation(position.posX, position.posY + 1, position.posZ, playerMP.rotationPitch, playerMP.rotationYaw);
    }

    @Override
    public String getHelpInfo() {
        return "Teleports a player to Spawn";
    }
}
