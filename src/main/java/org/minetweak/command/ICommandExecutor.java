package org.minetweak.command;

public interface ICommandExecutor {
    public void executeCommand(CommandSender sender, String commandName, String[] args);
}
