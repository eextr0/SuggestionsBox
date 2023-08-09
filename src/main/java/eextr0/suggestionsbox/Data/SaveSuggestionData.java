package eextr0.suggestionsbox.Data;

import eextr0.suggestionsbox.SuggestionsBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SaveSuggestionData {

    private final SuggestionsBox plugin;

    public SaveSuggestionData(SuggestionsBox plugin) {
        this.plugin = plugin;
    }

    public void saveSuggestions(List<SuggestionData> suggestionList) {
        try (PreparedStatement insertStatement = plugin.getConnection().prepareStatement(
                "INSERT INTO suggestions (title, body, author, tag) VALUES (?, ?, ?, ?)"
        )) {
            try (PreparedStatement updateStatement = plugin.getConnection().prepareStatement(
                    "UPDATE suggestions SET tag = ? WHERE title = ? AND body = ? AND author = ?"
            )) {
                for (SuggestionData suggestionData : suggestionList) {
                    if (isSuggestionExist(suggestionData)) {
                        if (!isAuthorMatching(suggestionData)) {
                            insertStatement.setString(1, suggestionData.getTitle());
                            insertStatement.setString(2, suggestionData.getBody());
                            insertStatement.setString(3, suggestionData.getAuthor());
                            insertStatement.setString(4, suggestionData.getTag());
                            insertStatement.executeUpdate();
                        } else {
                            updateStatement.setString(1, suggestionData.getTag());
                            updateStatement.setString(2, suggestionData.getTitle());
                            updateStatement.setString(3, suggestionData.getBody());
                            updateStatement.setString(4, suggestionData.getAuthor());
                            updateStatement.executeUpdate();
                        }
                    } else {
                        insertStatement.setString(1, suggestionData.getTitle());
                        insertStatement.setString(2, suggestionData.getBody());
                        insertStatement.setString(3, suggestionData.getAuthor());
                        insertStatement.setString(4, suggestionData.getTag());
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isSuggestionExist(SuggestionData suggestionData) throws SQLException {
        try (PreparedStatement statement = plugin.getConnection().prepareStatement(
                "SELECT * FROM suggestions WHERE title = ? AND body = ? AND author = ?"
        )) {
            statement.setString(1, suggestionData.getTitle());
            statement.setString(2, suggestionData.getBody());
            statement.setString(3, suggestionData.getAuthor());

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private boolean isAuthorMatching(SuggestionData suggestionData) throws SQLException {
        try (PreparedStatement statement = plugin.getConnection().prepareStatement(
                "SELECT * FROM suggestions WHERE title = ? AND body = ? AND author = ? AND tag = ?"
        )) {
            statement.setString(1, suggestionData.getTitle());
            statement.setString(2, suggestionData.getBody());
            statement.setString(3, suggestionData.getAuthor());
            statement.setString(4, suggestionData.getTag());

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}