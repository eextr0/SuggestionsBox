package eextr0.suggestionsbox.Data.DatabaseManagers;

import eextr0.suggestionsbox.SuggestionsBox;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectors {

    private Connection connection;
    private final SuggestionsBox plugin;
    private static final String DB_FILE_NAME = "SuggestionsBox.suggestions.db";

    public DatabaseConnectors(SuggestionsBox plugin) {
        this.plugin = plugin;
    }

    public void connectToDatabase() {
        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File dbFile = new File(dataFolder, DB_FILE_NAME);
            if (!dbFile.exists()) {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath());
            plugin.setConnection(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectFromDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                plugin.setConnection(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reloadDatabase() {
        disconnectFromDatabase();
        connectToDatabase();
    }
}
