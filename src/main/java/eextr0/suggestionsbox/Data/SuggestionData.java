package eextr0.suggestionsbox.Data;

import eextr0.suggestionsbox.SuggestionsBox;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuggestionData {

    private final SuggestionsBox plugin;
    private final String title;
    private String body;
    private String tag;
    private final String author;

    public SuggestionData(SuggestionsBox plugin, String title, String body, String author) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.tag = "draft";
        this.plugin = plugin;
    }

    public String getTitle() {return title;}
    public String getBody() {return body;}
    public String getTag() {return tag;}
    public String getAuthor() {return author;}
    public void setTag(String tag) {
        this.tag = tag;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public List<SuggestionData> getSuggestionsFromDatabase(String tag) {
        List<SuggestionData> suggestions = new ArrayList<>();

        try(PreparedStatement preparedStatement = plugin.getConnection().prepareStatement(
                "SELECT * FROM suggestions WHERE tag = ?"
        )) {
            preparedStatement.setString(1, tag);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String title = resultSet.getString("title");
                String body = resultSet.getString("body");
                String author = resultSet.getString("author");
                SuggestionData suggestionData = new SuggestionData(plugin, title, body, author);
                suggestions.add(suggestionData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suggestions;
    }
}
