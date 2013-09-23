package org.minetweak.command;

import net.minecraft.server.MinecraftServer;
import org.minetweak.chat.TextColor;
import org.minetweak.config.GameConfig;
import org.minetweak.server.Difficulty;
import org.minetweak.server.Server;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandDifficulty extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.difficulty")) {
            noPermission(sender, "change world difficulty");
            return;
        } else if (args.length != 1) {
            sender.sendMessage("Usage: /difficulty <difficulty>");
            return;
        }

        Difficulty parsedDifficulty = Difficulty.getByName(args[0]);
        if (parsedDifficulty != null) {
            GameConfig.set("server.difficulty", String.valueOf(parsedDifficulty.getID()));
            MinecraftServer.getServer().setDifficultyForAllWorlds(parsedDifficulty.getID());
            Server.sendToOps(TextColor.GREEN + "Difficulty was changed to: " + TextColor.RED + "peaceful");
        } else {
            sender.sendMessage(TextColor.RED + "That difficulty does not exist.");
        }
    }

    @Override
    public String getHelpInfo() {
        return "Set the server difficulty";
    }

    @Override
    public void getTabCompletion(CommandSender sender, String input, ArrayList<String> completions) {
        String[] split = input.split(" ");
        if (split.length == 2) {
            String currentDifficulty = split[1];
            for (String diff : Arrays.asList("peaceful", "easy", "hard", "normal")) {
                if (diff.startsWith(currentDifficulty)) {
                    completions.add(diff);
                }
            }
        } else if (split.length == 1) {
            completions.addAll(Arrays.asList("peaceful", "easy", "hard", "normal"));
        }
    }
}
