package org.minetweak.chat;

import org.minetweak.Minetweak;
import org.minetweak.command.CommandExecutor;
import org.minetweak.command.CommandSender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TabCompletion {
    /**
     * Gets all Commands that are Registered
     * @return List of Commands Registered
     */
    public static Set<String> getCommands() {
        return Minetweak.getCommandExecutors().keySet();
    }

    /**
     * Used to get all matches for the input
     * @param sender Command Sender
     * @param input input to parse
     * @return matches
     */
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

    /**
     * Gets all Players Matching the Input
     * @param input input to parse
     * @return players matching input
     */
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

    /**
     * Gets the Commands Matching the Input
     * @param input input line
     * @param addSlash whether to add the slash to the output
     * @return Commands Matching Input
     */
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

    /**
     * Gets the Commands Matching the Input with the Slashes added
     * @param input input line
     * @return Commands Matching Input
     */
    public static Set<String> getCommandsMatching(String input) {
        return getCommandsMatching(input, true);
    }

    /**
     * Gets the Players for a command that only needs players in the completions
     * @param input input to parse
     * @param completions completions list to add to
     */
    public static void getPlayersOnlyCommand(String input, ArrayList<String> completions) {
        String[] split = input.split(" ");

        if (split.length == 1) {
            completions.addAll(Minetweak.getPlayers().keySet());
        } else if (split.length == 2) {
            completions.addAll(TabCompletion.getPlayersMatching(split[1]));
        }
    }
}
