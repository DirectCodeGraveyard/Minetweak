package net.minecraft.command;

import net.minecraft.entity.EntityPlayerMP;
import net.minecraft.src.DamageSource;
import net.minecraft.utils.chat.ChatMessageComponent;

public class CommandKill extends CommandBase {
    public String getCommandName() {
        return "kill";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel() {
        return 0;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender) {
        return "commands.kill.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
        EntityPlayerMP var3 = getCommandSenderAsPlayer(par1ICommandSender);
        var3.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
        par1ICommandSender.func_110122_a(ChatMessageComponent.createPremade("commands.kill.success"));
    }
}
