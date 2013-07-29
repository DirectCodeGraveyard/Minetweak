package org.minetweak.command;

import org.minetweak.chat.TextColor;
import org.minetweak.config.MinetweakConfig;
import org.minetweak.entity.Player;
import org.minetweak.server.Difficulty;
import org.minetweak.util.StringUtils;
import org.minetweak.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandDifficulty extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.difficulty")) {
            noPermission(sender, "change world difficulty");
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players Can change the world difficulty.");
            return;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /" + overallCommand + " ID/NAME");
            return;
        }
        Player player = (Player) sender;

        int id;
        boolean isID = StringUtils.isInteger(args[0]);
        String arg1 = args[0].toLowerCase();

        if (isID) {
            id = Integer.parseInt(arg1);
        } else {
            id = Difficulty.getByName(arg1).getID();
        }
        MinetweakConfig.set("server.difficulty", String.valueOf(id));
        Difficulty difficulty = Difficulty.getByID(id);
        World world = player.getCurrentWorld();
        world.setDifficulty(difficulty);

        world.broadcastMessage("Set difficulty to " + TextColor.GREEN + difficulty.getName());
    }

    @Override
    public String getHelpInfo() {
        return "Sets the Worlds Difficulty";
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
