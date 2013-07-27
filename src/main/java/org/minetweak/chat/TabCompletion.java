package org.minetweak.chat;

import org.minetweak.Minetweak;
import org.minetweak.command.CommandExecutor;
import org.minetweak.command.CommandSender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TabCompletion {
    public static Set<String> getCommands() {
        return Minetweak.getCommandExecutors().keySet();
    }

    public static ArrayList<String> getMatches(CommandSender sender, String input) {
        ArrayList<String> matches = new ArrayList<String>();
        String[] split = input.split(" ");
        if (input.startsWith("/")) {
            String foundCommand = split[0].substring(1);
            if (getCommands().contains(foundCommand)) {
                if (input.contains(" ")) {
                    CommandExecutor executor = Minetweak.getCommandExecutors().get(foundCommand);
                    executor.getTabCompletion(sender, input, matches);
                    return matches;
                } else {
                    // No Matches, because the user must add a space by themselves
                    return matches;
                }
            }
            Set<String> commands = getCommands();
            input = input.substring(1);
            if (!input.isEmpty()) {
                return new ArrayList<String>(getCommandsMatching(input));
            } else {
                for (String command : commands) {
                    matches.add("/" + command);
                }
            }
        } else {
            matches = new ArrayList<String>(getPlayersMatching(input));
        }
        return matches;
    }

    public static Set<String> getPlayersMatching(String input) {
        Set<String> matches = new HashSet<String>();
        if (input.isEmpty()) {
            return Minetweak.getPlayers().keySet();
        } else {
            Set<String> players = Minetweak.getPlayers().keySet();
            for (String player : players) {
                if (player.startsWith(input)) {
                    matches.add(player);
                }
            }
        }
        return matches;
    }

    public static Set<String> getCommandsMatching(String input, boolean addSlash) {
        Set<String> commands = getCommands();
        Set<String> matches = new HashSet<String>();
        if (input.startsWith("/")) {
            input = input.substring(1);
        }
        input = input.split(" ")[0];

        for (String cmd : commands) {
            if (cmd.startsWith(input)) {
                if (addSlash) {
                    matches.add("/" + cmd);
                } else {
                    matches.add(cmd);
                }
            }
        }
        return matches;
    }

    public static Set<String> getCommandsMatching(String input) {
        return getCommandsMatching(input, true);
    }

    public static void getPlayersOnlyCommand(String input, ArrayList<String> completions) {
        String[] split = input.split(" ");

        if (split.length == 1) {
            completions.addAll(Minetweak.getPlayers().keySet());
        } else if (split.length == 2) {
            completions.addAll(TabCompletion.getPlayersMatching(split[1]));
        }
    }
}
