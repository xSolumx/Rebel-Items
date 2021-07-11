package com.mcrebels.rebelitems.rebelitems.allItems;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.mcrebels.rebelitems.rebelitems.RebelItems.getPlugin;

public class testItem extends Item implements Listener {
    private ItemStack item;

    private final Integer customMetaID = 1;
    private final String configID = "testitem";
    private final Component itemName;
    private final Material itemMaterial = Material.DIAMOND_SWORD;
    private final List<Component> itemLore;
    private static Plugin plugin;



    public testItem(){
        plugin = getPlugin();
        item = new ItemStack(itemMaterial, 1);
        itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>" + plugin.getConfig().getString(configID + ".displayname") + "</gradient>");
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
                MiniMessage.markdown().parse("<gradient:green:blue>" + plugin.getConfig().getString(configID + ".lore1") + "</gradient>"),
                MiniMessage.markdown().parse("third line"),
                MiniMessage.markdown().parse("<blue>fourth line"));
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    @Override
    public String getName() {
        return "TestItem";
    }

    public ItemStack getItem(){
        return item;
    }

    public void reloadConfig(FileConfiguration config) {

    }
}
