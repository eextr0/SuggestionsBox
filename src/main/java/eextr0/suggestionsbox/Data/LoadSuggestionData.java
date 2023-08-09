package eextr0.suggestionsbox.Data;

import eextr0.suggestionsbox.SuggestionsBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadSuggestionData {

    private final SuggestionsBox plugin;

    public LoadSuggestionData (SuggestionsBox plugin) {
        this.plugin = plugin;
    }
    public void getSuggestionsFromDatabase() {

        try(PreparedStatement preparedStatement = plugin.getConnection().prepareStatement(
                "SELECT * FROM suggestions"
        )) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String title = resultSet.getString("title");
                String body = resultSet.getString("body");
                String author = resultSet.getString("author");
                String tag = resultSet.getString("tag");

                SuggestionData suggestionData = new SuggestionData(plugin, title, body, author, tag);
                plugin.addToSuggestionList(suggestionData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
