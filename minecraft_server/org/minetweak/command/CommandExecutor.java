package org.minetweak.command;

public abstract class CommandExecutor {

    public abstract void executeCommand(CommandSender sender, String overallCommand, String[] args);

    String createString(String[] args, int start) {
        return createString(args, start, " ");
    }

    String createString(String[] args, int start, String glue) {
        StringBuilder string = new StringBuilder();

        for (int x = start; x < args.length; x++) {
            string.append(args[x]);
            if (x != args.length - 1) {
                string.append(glue);
            }
        }

        return string.toString();
    }

}
