package org.minetweak.command;

public abstract class CommandExecutor {

    public abstract void executeCommand(CommandSender sender, String overallCommand, String[] args);

    public String getHelpInfo() {
        return "";
    }

    public static String mergeArgs(String[] args) {
        String s = "";
        for (String s1 : args)
            s += s1 + " ";
        return s.trim();
    }

}
