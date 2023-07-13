package eextr0.suggestionsbox.Config;
import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import java.util.HashMap;
import java.util.Map;

public class MessagesConfigManager extends ConfigManager {

    private final Map<String, String> errorMessages = new HashMap<>();
    private final Map<String, String> commandMessages = new HashMap<>();
    private final Map<String,String> broadcastMessages = new HashMap<>();
    public MessagesConfigManager(SuggestionsBox plugin) {
        super(plugin);
        if(plugin.messagesFile != null && plugin.messagesConfigStream != null) {
            updateConfig(plugin.messagesFile, plugin.messagesConfigStream);
        }
        createConfig("messages.yml");

        load();
    }

    public void load() {
        ConfigurationSection errorSection = getConfig().getConfigurationSection("messages.error");
        assert errorSection != null;
        for (String key : errorSection.getKeys(false)) {
            String message = translateText(errorSection, key);
            errorMessages.put(key, message);
        }

        ConfigurationSection commandSection = getConfig().getConfigurationSection("messages.commands");
        assert commandSection != null;
        for (String key : commandSection.getKeys(false)) {
            String message = translateText(commandSection, key);
            commandMessages.put(key, message);
        }
        ConfigurationSection inputResponseSection = getConfig().getConfigurationSection("messages.inputResponse");
        assert inputResponseSection != null;
        for (String key: inputResponseSection.getKeys(false)) {
            String message = translateText(inputResponseSection, key);
            broadcastMessages.put(key, message);
        }



    }
    public String translateText(ConfigurationSection config, String key) {

        String text = config.getString(key);
        String translatedText = "";
        assert text != null;
        if (!text.isEmpty()) {
            translatedText = ChatColor.translateAlternateColorCodes('&', text);
        }
        return translatedText;
    }

    public Map<String, String> getErrorMessages() {return errorMessages;}
    public Map<String, String> getCommandMessages() {return commandMessages;}
    public Map<String, String> getInputResponseMessages() {return broadcastMessages;}

}

