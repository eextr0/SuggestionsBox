package eextr0.suggestionsbox.Utils;

import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PaperCreator {

    private final SuggestionsBox plugin;
    private final int LINE_LENGTH = 50;

    public PaperCreator(SuggestionsBox plugin) {
        this.plugin = plugin;
    }

    public ItemStack createPaper(String title, String body, String author) {
        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();

        assert paperMeta != null;
        paperMeta.setDisplayName(title + " - " + author);
        paperMeta.setLore(wrapText(body, LINE_LENGTH));

        paper.setItemMeta(paperMeta);

        return paper;
    }

    private List<String> wrapText(String text, int lineLength) {
        String [] words = text.split( " ");
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() <= lineLength) {
                currentLine.append(word).append(" ");
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word + " ");
            }
        }

        lines.add(currentLine.toString());

        return lines;
    }
}
