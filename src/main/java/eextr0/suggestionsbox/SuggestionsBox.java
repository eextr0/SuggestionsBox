package eextr0.suggestionsbox;

import eextr0.suggestionsbox.Commands.SuggestionsBoxCommands;
import eextr0.suggestionsbox.Config.MessagesConfigManager;
import eextr0.suggestionsbox.Data.DatabaseManagers.CreateTable;
import eextr0.suggestionsbox.Data.DatabaseManagers.DatabaseConnectors;
import eextr0.suggestionsbox.Data.PlayerInputData;
import eextr0.suggestionsbox.Data.SuggestionData;
import eextr0.suggestionsbox.Listeners.PlayerChatListener;
import eextr0.suggestionsbox.Listeners.PlayerInputCancelListener;
import eextr0.suggestionsbox.Listeners.PlayerQuitListener;
import eextr0.suggestionsbox.Tasks.TimeoutTask;
import org.bukkit.plugin.java.JavaPlugin;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class SuggestionsBox extends JavaPlugin {

    private MessagesConfigManager messagesConfigManager;
    private PlayerInputData playerInputData;
    private TimeoutTask timeoutTask;
    private Connection connection;
    private DatabaseConnectors databaseConnectors;
    private SuggestionData suggestionData;
    private List<SuggestionData> suggestionList = new ArrayList<>();

    //Getters
    public Connection getConnection() {return connection;}
    public MessagesConfigManager getMessagesConfigManager() {return messagesConfigManager;}
    public TimeoutTask getTimeoutTask() {return timeoutTask;}
    public PlayerInputData getPlayerInputData() {return playerInputData;}
    //Setters
    public Connection setConnection(Connection connection) {this.connection = connection;
        return connection;}
    public List<SuggestionData> getSuggestionList() {return suggestionList;}
    public List<SuggestionData> setSuggestionList(String tag) {this.suggestionList = suggestionData.getSuggestionsFromDatabase(tag);
    return suggestionList;}

    public File messagesFile;
    public InputStream messagesConfigStream;

    public HashMap<UUID, PlayerInputData> playerInputMap;
    @Override
    public void onEnable() {
        //Initialize commands and listeners
        getCommand("suggestion").setExecutor(new SuggestionsBoxCommands(this));
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInputCancelListener(this), this);
        playerInputMap = new HashMap<>();

        //Initialize Configs
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        this.messagesConfigStream = getResource("messages.yml");
        this.messagesFile = new File(getDataFolder(), "messages.yml");
        this.messagesConfigManager = new MessagesConfigManager(this);

        //Initialize classes
        this.playerInputData = new PlayerInputData(null, this);

        //Initialize database
        DatabaseConnectors databaseConnectors = new DatabaseConnectors(this);
        databaseConnectors.connectToDatabase();
        CreateTable createTable = new CreateTable(this);
        createTable.createTable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
