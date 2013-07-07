package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

import java.util.Iterator;

public class ServerCommandManager extends CommandHandler implements IAdminCommand {
    public ServerCommandManager() {
    }

    /**
     * Sends a message to the admins of the server from a given CommandSender with the given resource string and given
     * extra srings. If the int par2 is even or zero, the original sender is also notified.
     */
    public void notifyAdmins(ICommandSender par1ICommandSender, int par2, String par3Str, Object... par4ArrayOfObj) {
        boolean var5 = true;

        if (par1ICommandSender instanceof TileEntityCommandBlock && !MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput")) {
            var5 = false;
        }

        ChatMessageComponent var6 = ChatMessageComponent.func_111082_b("chat.type.admin", new Object[]{par1ICommandSender.getCommandSenderName(), ChatMessageComponent.func_111082_b(par3Str, par4ArrayOfObj)});
        var6.func_111059_a(EnumChatFormatting.GRAY);
        var6.func_111063_b(Boolean.valueOf(true));

        if (var5) {
            Iterator var7 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();

            while (var7.hasNext()) {
                EntityPlayerMP var8 = (EntityPlayerMP) var7.next();

                if (var8 != par1ICommandSender && MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(var8.getCommandSenderName())) {
                    var8.func_110122_a(var6);
                }
            }
        }

        if (par1ICommandSender != MinecraftServer.getServer()) {
            MinecraftServer.getServer().func_110122_a(var6);
        }

        if ((par2 & 1) != 1) {
            par1ICommandSender.func_110122_a(ChatMessageComponent.func_111082_b(par3Str, par4ArrayOfObj));
        }
    }
}
