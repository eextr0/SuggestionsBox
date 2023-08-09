package eextr0.suggestionsbox.Data;

import eextr0.suggestionsbox.SuggestionsBox;

public class SuggestionData {

    private final SuggestionsBox plugin;
    private final String title;
    private String body;
    private String tag;
    private final String author;

    public SuggestionData(SuggestionsBox plugin, String title, String body, String author, String tag) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.tag = tag;
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
}
