package org.minetweak.command;

import net.minecraft.entity.EntityGiantZombie;
import net.minecraft.entity.EntityList;
import org.minetweak.entity.Player;
import org.minetweak.world.EntitySpawner;

public class CommandGiant extends CommandExecutor {
    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.isPlayer()) {
            notPlayer(sender);
            return;
        }
        Player player = (Player) sender;
        EntitySpawner.spawnCreature(player.getCurrentWorld(), EntityList.getEntityID(new EntityGiantZombie(player.getCurrentWorld().getWorldServer())), player.getX(), player.getY(), player.getZ());
    }
}
