package com.mcrebels.rebelitems.rebelitems.allItems.misc_Paper;

import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class token extends Item {
    private ItemStack item;

    private final Integer customMetaID = 3;
    private final Component itemName = MiniMessage.markdown().parse("<blue>Token");
    private final Material itemMaterial = Material.PAPER;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:gold:yellow>Token</gradient>"));




    public token(){
        item = new ItemStack(itemMaterial, 1);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    public token(int amount){
        item = new ItemStack(itemMaterial, amount);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    public void checkBounds(Player player) {
        //TODO with updateChance function
    }

    @Override
    public String getName() {
        return "Token";
    }

    public ItemStack getItem()
    {
        return item;
    }
}
