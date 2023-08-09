package eextr0.suggestionsbox;

import eextr0.suggestionsbox.Commands.SuggestionsBoxCommands;
import eextr0.suggestionsbox.Config.MessagesConfigManager;
import eextr0.suggestionsbox.Data.DatabaseManagers.CreateTable;
import eextr0.suggestionsbox.Data.DatabaseManagers.DatabaseConnectors;
import eextr0.suggestionsbox.Data.LoadSuggestionData;
import eextr0.suggestionsbox.Data.PlayerInputData;
import eextr0.suggestionsbox.Data.SaveSuggestionData;
import eextr0.suggestionsbox.Data.SuggestionData;
import eextr0.suggestionsbox.GUI.CreateGUI;
import eextr0.suggestionsbox.GUI.RegisterGUI;
import eextr0.suggestionsbox.Listeners.*;
import eextr0.suggestionsbox.Tasks.TimeoutTask;
import eextr0.suggestionsbox.Utils.PaperCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.*;

public final class SuggestionsBox extends JavaPlugin {

    private MessagesConfigManager messagesConfigManager;
    private PlayerInputData playerInputData;
    private TimeoutTask timeoutTask;
    private Connection connection;
    private DatabaseConnectors databaseConnectors;
    private SuggestionData suggestionData;
    private PaperCreator paperCreator;
    private CreateGUI createGUI;
    private RegisterGUI registerGUI;
    private TextInputManager textInputManager;
    private List<SuggestionData> suggestionList = new ArrayList<>();
    private boolean guiOpen;

    //Getters
    public Connection getConnection() {return connection;}
    public MessagesConfigManager getMessagesConfigManager() {return messagesConfigManager;}
    public TimeoutTask getTimeoutTask() {return timeoutTask;}
    public PlayerInputData getPlayerInputData() {return playerInputData;}
    public PaperCreator getPaperCreator() {return paperCreator;}
    public CreateGUI createGUI() {return createGUI;}
    public List<SuggestionData> getSuggestionList() {return suggestionList;}
    public RegisterGUI checkRegisterGUI() {return registerGUI;}
    public TextInputManager getTextInputManager() {return textInputManager;}

    //Setters
    public Connection setConnection(Connection connection) {this.connection = connection;
        return connection;}

    public void addToSuggestionList(SuggestionData suggestionData) {this.suggestionList.add(suggestionData);}

    public void setSuggestionList(List<SuggestionData> suggestionList) {this.suggestionList = suggestionList;}

    public RegisterGUI registerGUI(Player player,
                                   Inventory gui,
                                   int currentPage,
                                   ArrayList<ItemStack> itemList,
                                   String title) {
        this.registerGUI.registerGUI(player,
                gui,
                currentPage,
                itemList,
                title);
    return registerGUI;}

    public File messagesFile;
    public InputStream messagesConfigStream;

    public HashMap<UUID, PlayerInputData> playerInputMap;
    @Override
    public void onEnable() {
        //Initialize commands and listeners
        getCommand("suggestion").setExecutor(new SuggestionsBoxCommands(this));
        getServer().getPluginManager().registerEvents(new ApprovalListener(this), this);
        getServer().getPluginManager().registerEvents(new PageNavigationListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerClickListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInputCancelListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        getServer().getPluginManager().registerEvents(new TextInputManager(this),this);


        textInputManager = new TextInputManager(this);
        playerInputMap = new HashMap<>();

        //Initialize Configs
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        this.messagesConfigStream = getResource("messages.yml");
        this.messagesFile = new File(getDataFolder(), "messages.yml");
        this.messagesConfigManager = new MessagesConfigManager(this);

        //Initialize classes
        this.playerInputData = new PlayerInputData(null, this);
        this.paperCreator = new PaperCreator(this);
        this.createGUI = new CreateGUI(this);
        this.registerGUI = new RegisterGUI();
        LoadSuggestionData loadSuggestionData = new LoadSuggestionData(this);

        //Initialize database
        databaseConnectors = new DatabaseConnectors(this);
        databaseConnectors.connectToDatabase();
        CreateTable createTable = new CreateTable(this);
        createTable.createTable();
        loadSuggestionData.getSuggestionsFromDatabase();

    }

    @Override
    public void onDisable() {

        //save and disconnect database
        SaveSuggestionData saveSuggestionData = new SaveSuggestionData(this);
        saveSuggestionData.saveSuggestions(suggestionList);
        databaseConnectors.disconnectFromDatabase();
    }
}
