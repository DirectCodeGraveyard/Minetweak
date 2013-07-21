package org.minetweak.command;

import org.minetweak.Minetweak;

import java.util.Set;

public class CmdHelper {
    public static Set<String> getCommands() {
        return Minetweak.getCommandExecutors().keySet();
    }
}
