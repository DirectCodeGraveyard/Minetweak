package org.minetweak.chat;

import org.minetweak.Minetweak;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TabCompletion {
    public static Set<String> getCommands() {
        return Minetweak.getCommandExecutors().keySet();
    }

    public static ArrayList<String> getMatches(String input) {
        Set<String> matches = new HashSet<String>();
        if (input.startsWith("/")) {
            Set<String> commands = getCommands();
            input = input.substring(1);
            if (!input.equals("")) {
                return new ArrayList<String>(getCommandsMatching(input));
            } else {
                for (String command : commands) {
                    matches.add("/" + command);
                }
            }
        } else {
            matches = getPlayersMatching(input);
        }
        return new ArrayList<String>(matches);
    }

    public static Set<String> getPlayersMatching(String input) {
        Set<String> matches = new HashSet<String>();
        if (input.equals("")) {
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

    public static Set<String> getCommandsMatching(String input) {
        Set<String> commands = getCommands();
        Set<String> matches = new HashSet<String>();
        if (input.startsWith("/")) {
            input = input.substring(1);
        }
        if (input.contains(" ")) {
            return getPlayersMatching("");
        }
        for (String cmd : commands) {
            if (cmd.startsWith(input)) {
                matches.add("/" + cmd);
            }
        }
        return matches;
    }
}
