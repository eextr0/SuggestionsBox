package eextr0.suggestionsbox.GUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterGUI {
    private Map<Player, GUIInfo> playerGUIs = new HashMap<>();

    // ... other plugin code ...

    public void registerGUI(Player player, Inventory gui, int currentPage, ArrayList<ItemStack> itemList, String title) {
        GUIInfo guiInfo = new GUIInfo(gui, currentPage, itemList, title);
        playerGUIs.put(player, guiInfo);
    }

    public boolean isRegisteredGUI(Player player, Inventory gui) {
        GUIInfo guiInfo = playerGUIs.get(player);
        return guiInfo != null && guiInfo.getGui().equals(gui);
    }

    public Inventory getPlayerGUI(Player player) {
        GUIInfo guiInfo = playerGUIs.get(player);
        return guiInfo != null ? guiInfo.getGui() : null;
    }

    public int getCurrentPage(Player player) {
        GUIInfo guiInfo = playerGUIs.get(player);
        return guiInfo != null ? guiInfo.getCurrentPage() : 1;
    }

    public ArrayList<ItemStack> getItemList(Player player) {
        GUIInfo guiInfo = playerGUIs.get(player);
        return guiInfo != null ? guiInfo.getItemList() : null;
    }

    public String getTitle(Player player) {
        GUIInfo guiInfo = playerGUIs.get(player);
        return guiInfo != null ? guiInfo.getTitle() : null;
    }
    private static class GUIInfo {
        private Inventory gui;
        private int currentPage;
        private ArrayList<ItemStack> itemList;
        private String title;

        public GUIInfo(Inventory gui, int currentPage, ArrayList<ItemStack> itemList, String title) {
            this.gui = gui;
            this.currentPage = currentPage;
            this.itemList = itemList;
            this.title = title;
        }

        public Inventory getGui() {
            return gui;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public ArrayList<ItemStack> getItemList() {
            return itemList;
        }

        public String getTitle() {return title;}
    }
}
