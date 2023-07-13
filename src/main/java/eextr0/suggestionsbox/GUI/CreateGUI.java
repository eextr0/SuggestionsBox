package eextr0.suggestionsbox.GUI;

import eextr0.suggestionsbox.SuggestionsBox;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class CreateGUI {

    private final SuggestionsBox plugin;

    public CreateGUI(SuggestionsBox plugin) {
        this.plugin = plugin;
    }

    public void openUI(Player player, String title, ArrayList<ItemStack> itemList) {
        Inventory gui = Bukkit.createInventory(null, 54, title);

        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glassPane.getItemMeta();
        glassMeta.setDisplayName(" ");
        glassPane.setItemMeta(glassMeta);
        Arrays.fill(gui.getContents(), glassPane);

        for (int i = 10; i <= 17; i++) {
            gui.setItem(i, itemList.get(i - 9));

        }
        for (int i = 20; i <= 27; i++) {
            gui.setItem(i, itemList.get(i - 11));

        }
        for (int i = 20; i <= 27; i++) {
            gui.setItem(i, itemList.get(i-13));
        }
        player.openInventory(gui);
    }


}
