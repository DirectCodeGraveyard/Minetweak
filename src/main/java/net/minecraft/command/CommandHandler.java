package net.minecraft.command;

import net.minecraft.crash.exception.WrongUsageException;
import net.minecraft.entity.EntityPlayerMP;
import net.minecraft.player.PlayerSelector;
import net.minecraft.utils.chat.ChatMessageComponent;
import net.minecraft.utils.enums.EnumChatFormatting;

import java.util.*;
import java.util.Map.Entry;

public class CommandHandler implements ICommandManager {
    /**
     * Map of Strings to the ICommand objects they represent
     */
    private final Map<String, ICommand> commandMap = new HashMap<String, ICommand>();

    /**
     * The set of ICommand objects currently loaded.
     */
    private final Set<ICommand> commandSet = new HashSet<ICommand>();

    public int executeCommand(ICommandSender par1ICommandSender, String par2Str) {
        par2Str = par2Str.trim();

        if (par2Str.startsWith("/")) {
            par2Str = par2Str.substring(1);
        }

        String[] var3 = par2Str.split(" ");
        String var4 = var3[0];
        var3 = dropFirstString(var3);
        ICommand var5 = this.commandMap.get(var4);
        int var6 = this.getUsernameIndex(var5, var3);
        int var7 = 0;

        try {
            if (var5 == null) {
                throw new CommandNotFoundException();
            }

            if (var5.canCommandSenderUseCommand(par1ICommandSender)) {
                if (var6 > -1) {
                    EntityPlayerMP[] var8 = PlayerSelector.matchPlayers(par1ICommandSender, var3[var6]);
                    String var9 = var3[var6];

                    for (EntityPlayerMP var13 : var8) {
                        var3[var6] = var13.getEntityName();

                        try {
                            var5.processCommand(par1ICommandSender, var3);
                            ++var7;
                        } catch (CommandException var15) {
                            par1ICommandSender.func_110122_a(ChatMessageComponent.createWithType(var15.getMessage(), var15.getErrorOjbects()).func_111059_a(EnumChatFormatting.RED));
                        }
                    }

                    var3[var6] = var9;
                } else {
                    var5.processCommand(par1ICommandSender, var3);
                    ++var7;
                }
            } else {
                par1ICommandSender.func_110122_a(ChatMessageComponent.createPremade("commands.generic.permission").func_111059_a(EnumChatFormatting.RED));
            }
        } catch (WrongUsageException var16) {
            par1ICommandSender.func_110122_a(ChatMessageComponent.createWithType("commands.generic.usage", ChatMessageComponent.createWithType(var16.getMessage(), var16.getErrorOjbects())).func_111059_a(EnumChatFormatting.RED));
        } catch (CommandException var17) {
            par1ICommandSender.func_110122_a(ChatMessageComponent.createWithType(var17.getMessage(), var17.getErrorOjbects()).func_111059_a(EnumChatFormatting.RED));
        } catch (Throwable var18) {
            par1ICommandSender.func_110122_a(ChatMessageComponent.createPremade("commands.generic.exception").func_111059_a(EnumChatFormatting.RED));
            var18.printStackTrace();
        }

        return var7;
    }

    /**
     * adds the command and any aliases it has to the internal map of available commands
     */
    public ICommand registerCommand(ICommand par1ICommand) {
        List var2 = par1ICommand.getCommandAliases();
        this.commandMap.put(par1ICommand.getCommandName(), par1ICommand);
        this.commandSet.add(par1ICommand);

        if (var2 != null) {

            for (Object aVar2 : var2) {
                String var4 = (String) aVar2;
                ICommand var5 = this.commandMap.get(var4);

                if (var5 == null || !var5.getCommandName().equals(var4)) {
                    this.commandMap.put(var4, par1ICommand);
                }
            }
        }

        return par1ICommand;
    }

    /**
     * creates a new array and sets elements 0..n-2 to be 0..n-1 of the input (n elements)
     */
    private static String[] dropFirstString(String[] par0ArrayOfStr) {
        String[] var1 = new String[par0ArrayOfStr.length - 1];

        System.arraycopy(par0ArrayOfStr, 1, var1, 0, par0ArrayOfStr.length - 1);

        return var1;
    }

    /**
     * Performs a "begins with" string match on each token in par2. Only returns commands that par1 can use.
     */
    public List getPossibleCommands(ICommandSender par1ICommandSender, String par2Str) {
        String[] var3 = par2Str.split(" ", -1);
        String var4 = var3[0];

        if (var3.length == 1) {
            ArrayList<String> var8 = new ArrayList<String>();

            for (Entry<String, ICommand> var7 : this.commandMap.entrySet()) {
                if (CommandBase.doesStringStartWith(var4, var7.getKey()) && (var7.getValue()).canCommandSenderUseCommand(par1ICommandSender)) {
                    var8.add(var7.getKey());
                }
            }

            return var8;
        } else {
            if (var3.length > 1) {
                ICommand var5 = this.commandMap.get(var4);

                if (var5 != null) {
                    return var5.addTabCompletionOptions(par1ICommandSender, dropFirstString(var3));
                }
            }

            return null;
        }
    }

    /**
     * returns all commands that the commandSender can use
     */
    public List getPossibleCommands(ICommandSender par1ICommandSender) {
        ArrayList<ICommand> var2 = new ArrayList<ICommand>();

        for (ICommand var4 : this.commandSet) {
            if (var4.canCommandSenderUseCommand(par1ICommandSender)) {
                var2.add(var4);
            }
        }

        return var2;
    }

    /**
     * returns a map of string to commads. All commands are returned, not just ones which someone has permission to use.
     */
    public Map getCommands() {
        return this.commandMap;
    }

    /**
     * Return a command's first parameter index containing a valid username.
     */
    private int getUsernameIndex(ICommand par1ICommand, String[] par2ArrayOfStr) {
        if (par1ICommand == null) {
            return -1;
        } else {
            for (int var3 = 0; var3 < par2ArrayOfStr.length; ++var3) {
                if (par1ICommand.isUsernameIndex(par2ArrayOfStr, var3) && PlayerSelector.matchesMultiplePlayers(par2ArrayOfStr[var3])) {
                    return var3;
                }
            }

            return -1;
        }
    }
}
