package net.minecraft.command;

import net.minecraft.crash.exception.NumberInvalidException;
import net.minecraft.crash.exception.WrongUsageException;
import net.minecraft.entity.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.utils.chat.ChatMessageComponent;
import net.minecraft.utils.enums.EnumChatFormatting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandHelp extends CommandBase {
    public String getCommandName() {
        return "help";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel() {
        return 0;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.help.usage";
    }

    public List getCommandAliases() {
        return Arrays.asList("?");
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        List var3 = this.getSortedPossibleCommands(par1ICommandSender);
        int var5 = (var3.size() - 1) / 7;
        ICommand var9;
        int var11;

        try {
            var11 = par2ArrayOfStr.length == 0 ? 0 : parseIntBounded(par1ICommandSender, par2ArrayOfStr[0], 1, var5 + 1) - 1;
        } catch (NumberInvalidException var10) {
            Map var8 = this.getCommands();
            var9 = (ICommand) var8.get(par2ArrayOfStr[0]);

            if (var9 != null) {
                throw new WrongUsageException(var9.getCommandUsage(par1ICommandSender));
            }

            throw new CommandNotFoundException();
        }

        int var7 = Math.min((var11 + 1) * 7, var3.size());
        par1ICommandSender.func_110122_a(ChatMessageComponent.createWithType("commands.help.header", var11 + 1, var5 + 1).func_111059_a(EnumChatFormatting.DARK_GREEN));

        for (int var12 = var11 * 7; var12 < var7; ++var12) {
            var9 = (ICommand) var3.get(var12);
            par1ICommandSender.func_110122_a(ChatMessageComponent.createPremade(var9.getCommandUsage(par1ICommandSender)));
        }

        if (var11 == 0 && par1ICommandSender instanceof EntityPlayer) {
            par1ICommandSender.func_110122_a(ChatMessageComponent.createPremade("commands.help.footer").func_111059_a(EnumChatFormatting.GREEN));
        }
    }

    /**
     * Returns a sorted list of all possible commands for the given ICommandSender.
     */
    protected List getSortedPossibleCommands(ICommandSender par1ICommandSender) {
        List var2 = MinecraftServer.getServer().getCommandManager().getPossibleCommands(par1ICommandSender);
        Collections.sort(var2);
        return var2;
    }

    protected Map getCommands() {
        return MinecraftServer.getServer().getCommandManager().getCommands();
    }
}
