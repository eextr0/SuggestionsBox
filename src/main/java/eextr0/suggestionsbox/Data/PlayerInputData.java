package eextr0.suggestionsbox.Data;

import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.entity.Player;

public class PlayerInputData {
    private final Player player;
    private SuggestionData suggestionData;
    private boolean waitingForInput;
    private boolean inputSessionComplete;
    private int currentInputIndex;
    private final String[] prompts;
    private final SuggestionsBox plugin;

    public PlayerInputData(Player player, SuggestionsBox plugin) {
        this.player = player;
        this.waitingForInput = false;
        this.inputSessionComplete = false;
        this.currentInputIndex = 0;
        this.plugin = plugin;
        this.prompts = new String[]{
                plugin.getMessagesConfigManager().getCommandMessages().get("suggestionCreateTitle"),
                plugin.getMessagesConfigManager().getCommandMessages().get("suggestionCreateBody")
        };
    }

    public void startInputSession() {
        waitingForInput = true;
        currentInputIndex = 0;
        promptNextInput();
    }

    public void promptNextInput() {
        if (currentInputIndex < 2) {
            player.sendMessage(prompts[currentInputIndex]);
        }
    }

    public void processInput(String input) {
        if (suggestionData == null) {
            suggestionData = new SuggestionData(plugin, input, null, player.getName());
            player.sendMessage("Suggestion title: " + input);
            } else {
            suggestionData.setBody(input);
            player.sendMessage("Your suggestion has been submitted for review.");
            suggestionData.setTag("under review");
            resetInputData();
        }
    }

    private void resetInputData() {
        suggestionData = null;
    }

    public void cancelInput() {
        waitingForInput = false;
        inputSessionComplete = true;
    }

    public boolean isWaitingForInput() {
        return waitingForInput;
    }

    public boolean isInputSessionComplete() {
        return inputSessionComplete;
    }

    public void setWaitingForInput(boolean waiting) {
        this.waitingForInput = waiting;
    }

}
