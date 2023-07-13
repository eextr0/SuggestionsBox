package eextr0.suggestionsbox.Commands;

import eextr0.suggestionsbox.Config.MessagesConfigManager;
import eextr0.suggestionsbox.Data.PlayerInputData;
import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SuggestionsBoxCommands implements TabExecutor {

    private final SuggestionsBox plugin;

    public SuggestionsBoxCommands(SuggestionsBox plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (args.length == 0) {
            commandSender.sendMessage("Plugin: SuggestionsBox");
            commandSender.sendMessage("Version: 1.0");
            commandSender.sendMessage("Author: eextr0");
        }

        String noPermission = plugin.getMessagesConfigManager().getErrorMessages().get("noPermission");

        switch (args[0]) {
            case "create" -> {
                if (commandSender instanceof Player p && p.hasPermission("suggestion.create")) {
                    if(plugin.playerInputMap.containsKey(p.getUniqueId())) {
                        p.sendMessage("You are already in an input session. Finish the current input first.");
                        return true;
                    }

                    PlayerInputData playerInputData = new PlayerInputData(p, plugin);
                    plugin.playerInputMap.put(p.getUniqueId(), playerInputData);
                    playerInputData.startInputSession();
                    return true;
                } else {
                    commandSender.sendMessage(noPermission);
                }
            }

            default ->
                    commandSender.sendMessage(plugin.getMessagesConfigManager().getErrorMessages().get("unknownCommand"));
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {





        return null;
    }
}
