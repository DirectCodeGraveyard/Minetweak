package net.minecraft.command;

import net.minecraft.entity.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.utils.chat.ChatMessageComponent;
import net.minecraft.world.World;

public class CommandShowSeed extends CommandBase {
    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
        return super.canCommandSenderUseCommand(par1ICommandSender);
    }

    public String getCommandName() {
        return "seed";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel() {
        return 2;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.seed.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        Object var3 = par1ICommandSender instanceof EntityPlayer ? ((EntityPlayer) par1ICommandSender).worldObj : MinecraftServer.getServer().worldServerForDimension(0);
        par1ICommandSender.func_110122_a(ChatMessageComponent.createWithType("commands.seed.success", new Object[]{Long.valueOf(((World) var3).getSeed())}));
    }
}
