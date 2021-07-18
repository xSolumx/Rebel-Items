package com.mcrebels.rebelitems.rebelitems.allItems.misc_Paper;

import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class currency extends Item {

    private ItemStack item;

    private final Integer customMetaID = 2;
    private final Component itemName = MiniMessage.markdown().parse("<white>Currency");
    private final Material itemMaterial = Material.PAPER;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:gold:yellow>Money</gradient>"));




    public currency(){
        item = new ItemStack(itemMaterial, 1);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    public currency(int amount){
        item = new ItemStack(itemMaterial, amount);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    @Override
    public String getName() {
        return "Currency";
    }

    public ItemStack getItem()
    {
        return item;
    }
}