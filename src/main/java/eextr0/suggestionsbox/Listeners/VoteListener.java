package eextr0.suggestionsbox.Listeners;

import eextr0.suggestionsbox.Data.SuggestionData;
import eextr0.suggestionsbox.GUI.RegisterGUI;
import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class VoteListener implements Listener {

    private final SuggestionsBox plugin;

    public VoteListener(SuggestionsBox plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();
        RegisterGUI registerGUI = plugin.checkRegisterGUI();
        if (!registerGUI.getTitle(player).equals("Suggestions Review")) {
            return;
        }

        if (clickedItem == null || clickedItem.getType() != Material.PAPER) {
            return;
        }
        if (registerGUI.isRegisteredGUI(player,inventory)) {
            int slot = event.getSlot();
            if (slot >= 0) {
                SuggestionData suggestionData = getSuggestionDataByDisplayName(clickedItem.getItemMeta().getDisplayName());
                if (suggestionData != null) {
                    if (event.isLeftClick()) {
                        System.out.println("tag before: " + suggestionData.getTag());
                        suggestionData.setTag("Approved");
                        System.out.println("tag after: " + suggestionData.getTag());
                        player.sendMessage("Suggestion approved.");
                        openSuggestionsReviewGUI(player);
                    } else if (event.isRightClick()) {
                        player.sendMessage("Please enter a reason for rejection in chat:");
                        CompletableFuture<String> inputFuture = plugin.getTextInputManager().requestInput(player);
                        inputFuture.thenAccept(reason -> {
                            if (!reason.isEmpty()) {
                                suggestionData.setTag("Rejected - " + reason);
                                player.sendMessage("Suggestion rejected with reason: " + reason);
                            } else {
                                player.sendMessage("Invalid reason. Suggestion rejection canceled.");
                            }
                            plugin.getServer().getScheduler().runTask(plugin, () -> openSuggestionsReviewGUI(player));
                        });
                        player.closeInventory();
                    }
                }
            }
        }
    }

    private SuggestionData getSuggestionDataByDisplayName(String displayName) {
        List<SuggestionData> suggestionList = plugin.getSuggestionList();

        for (SuggestionData suggestionData : suggestionList) {
            if (displayName.contains(suggestionData.getTitle()) && displayName.contains(suggestionData.getAuthor())) {
                return suggestionData;
            }
        }

        return null;
    }
    private void openSuggestionsReviewGUI(Player player) {
        if (player.hasPermission("suggestion.review")) {
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

            plugin.createGUI().openUI(player, "Suggestions Review", itemList, 1);
        }
    }
}