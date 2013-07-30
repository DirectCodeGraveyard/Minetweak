package net.minecraft.command;

import net.minecraft.crash.exception.WrongUsageException;
import net.minecraft.entity.EntityPlayerMP;
import net.minecraft.player.PlayerNotFoundException;
import net.minecraft.server.MinecraftServer;

import java.util.List;

public class CommandServerTp extends CommandBase {
    @Override
    public String getCommandName() {
        return "tp";
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.tp.usage";
    }

    @Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        if (par2ArrayOfStr.length < 1) {
            throw new WrongUsageException("commands.tp.usage");
        } else {
            EntityPlayerMP var3;

            if (par2ArrayOfStr.length != 2 && par2ArrayOfStr.length != 4) {
                var3 = getCommandSenderAsPlayer(par1ICommandSender);
            } else {
                var3 = func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);

                if (var3 == null) {
                    throw new PlayerNotFoundException();
                }
            }

            if (par2ArrayOfStr.length != 3 && par2ArrayOfStr.length != 4) {
                if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2) {
                    EntityPlayerMP var11 = func_82359_c(par1ICommandSender, par2ArrayOfStr[par2ArrayOfStr.length - 1]);

                    if (var11 == null) {
                        throw new PlayerNotFoundException();
                    }

                    if (var11.worldObj != var3.worldObj) {
                        notifyAdmins(par1ICommandSender, "commands.tp.notSameDimension");
                        return;
                    }

                    var3.mountEntity(null);
                    var3.playerNetServerHandler.setPlayerLocation(var11.posX, var11.posY, var11.posZ, var11.rotationYaw, var11.rotationPitch);
                    notifyAdmins(par1ICommandSender, "commands.tp.success", var3.getEntityName(), var11.getEntityName());
                }
            } else if (var3.worldObj != null) {
                int var4 = par2ArrayOfStr.length - 3;
                double var5 = func_110666_a(par1ICommandSender, var3.posX, par2ArrayOfStr[var4++]);
                double var7 = func_110665_a(par1ICommandSender, var3.posY, par2ArrayOfStr[var4++], 0, 0);
                double var9 = func_110666_a(par1ICommandSender, var3.posZ, par2ArrayOfStr[var4++]);
                var3.mountEntity(null);
                var3.setPositionAndUpdate(var5, var7, var9);
                notifyAdmins(par1ICommandSender, "commands.tp.success.coordinates", var3.getEntityName(), var5, var7, var9);
            }
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    @Override
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        return par2ArrayOfStr.length != 1 && par2ArrayOfStr.length != 2 ? null : getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    @Override
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2) {
        return par2 == 0;
    }
}
