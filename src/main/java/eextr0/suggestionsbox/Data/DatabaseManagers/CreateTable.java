package eextr0.suggestionsbox.Data.DatabaseManagers;

import eextr0.suggestionsbox.SuggestionsBox;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    private final SuggestionsBox plugin;

    public CreateTable(SuggestionsBox plugin) {
        this.plugin = plugin;
    }
    public void createTable() {
        try(Statement statement = plugin.getConnection().createStatement()) {
            statement.execute(
                    "CREATE TABLE IF NOT EXISTS suggestions (title Text,body TEXT, author TEXT, tag TEXT)"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
