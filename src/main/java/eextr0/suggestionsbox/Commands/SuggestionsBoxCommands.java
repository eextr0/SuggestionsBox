package eextr0.suggestionsbox.Commands;

import eextr0.suggestionsbox.Data.PlayerInputData;
import eextr0.suggestionsbox.Data.SuggestionData;
import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            case "review" -> {
                if (commandSender instanceof Player p && p.hasPermission("suggestion.review")) {
                    List<SuggestionData> suggestionList = plugin.getSuggestionList();
                    ArrayList<ItemStack> itemList = new ArrayList<>();

                    for (SuggestionData suggestion : suggestionList) {
                        if (suggestion.getTag().equals("Under Review")) {
                            ItemStack suggestionPaper = plugin.getPaperCreator().createPaper(
                                    suggestion.getTitle(),
                                    suggestion.getBody(),
                                    suggestion.getAuthor());
                            itemList.add(suggestionPaper);
                        }
                    }

                    plugin.createGUI().openUI(p, "Suggestions Review", itemList, 1);
                    return true;
                }
            }
            case "view" -> {
                if (commandSender instanceof Player p && p.hasPermission("suggestion.view")) {
                    List<SuggestionData> suggestionList = plugin.getSuggestionList();
                    ArrayList<ItemStack> itemList = new ArrayList<>();

                    for (SuggestionData suggestion : suggestionList) {
                        if (suggestion.getTag().equals("Approved")) {
                            ItemStack suggestionPaper = plugin.getPaperCreator().createPaper(
                                    suggestion.getTitle(),
                                    suggestion.getBody(),
                                    suggestion.getAuthor());
                            itemList.add(suggestionPaper);
                        }
                    }

                    plugin.createGUI().openUI(p, "Player Suggestions", itemList, 1);
                    return true;
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
