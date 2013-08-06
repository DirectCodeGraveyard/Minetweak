package org.minetweak.command;

import org.minetweak.chat.TextColor;
import org.minetweak.plugins.IPlugin;
import org.minetweak.plugins.PluginInfo;
import org.minetweak.plugins.PluginManager;

public class CommandPlugin extends CommandExecutor {

    @Override
    public void executeCommand(CommandSender sender, String overallCommand, String[] args) {
        if (!sender.hasPermission("minetweak.command.plugin")) {
            noPermission(sender, "query plugin information");
        }
        if (args.length > 1) {
            sender.sendMessage("Usage: /" + overallCommand + " [pluginName]");
            return;
        }
        if (args.length == 1) {
            String pluginName = args[0];
            IPlugin plugin = PluginManager.plugins.get(pluginName);
            if (plugin == null) {
                sender.sendMessage(TextColor.RED + "The Plugin \'" + pluginName + "\' does not exist!");
                return;
            }
            PluginInfo i = plugin.getPluginInfo();
            sender.sendMessage(new String[]{
                    format("ID", i.getName()),
                    format("Author", i.getAuthor()),
                    format("Description", i.getDescription())
            });
            return;
        }
        boolean flag = false;
        String plugins = "";
        for (String plugin : PluginManager.plugins.keySet()) {
            if (flag) {
                plugins += " " + plugin + "\n";
            } else {
                plugins += TextColor.BLUE + plugin;
                flag = true;
            }
        }
        sender.sendMessage("There are " + TextColor.GREEN + PluginManager.plugins.size() + TextColor.RESET + " plugins.");
        sender.sendMessage("There are " + TextColor.GREEN + PluginManager.enabledPlugins.size() + TextColor.RESET + " plugins enabled.");
        sender.sendMessage(plugins);
    }

    public String format(String name, String value) {
        return TextColor.BLUE + name + TextColor.RESET + ": " + value;
    }
}
