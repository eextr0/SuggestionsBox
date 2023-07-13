package eextr0.suggestionsbox.Data;

import eextr0.suggestionsbox.SuggestionsBox;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class saveSuggestionData {

    private final SuggestionsBox plugin;

    public saveSuggestionData (SuggestionsBox plugin) {
        this.plugin = plugin;
    }
    public void saveSuggestions(SuggestionData suggestionData) {
        try (PreparedStatement statement = plugin.getConnection().prepareStatement(
                "INSERT INTO suggestions (title, body, author, tag) VALUES (?, ?, ?, ?)"
        )) {
            statement.setString(1, suggestionData.getTitle());
            statement.setString(2, suggestionData.getBody());
            statement.setString(3, suggestionData.getAuthor());
            statement.setString(3, suggestionData.getTag());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
    }
    }
}
