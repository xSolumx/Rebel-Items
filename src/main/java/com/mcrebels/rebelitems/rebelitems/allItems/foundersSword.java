package com.mcrebels.rebelitems.rebelitems.allItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class foundersSword extends Item implements Listener{
    private ItemStack item;

    private final Integer customMetaID = 2;
    private final Component itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>Sword of the Founder</gradient>");
    private final Material itemMaterial = Material.NETHERITE_SWORD;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
            MiniMessage.markdown().parse("<gradient:green:blue>Bestowed Upon The Rebel Founders</gradient>"),
            MiniMessage.markdown().parse("For Their Undying Commitment"),
            MiniMessage.markdown().parse("<blue>Pebble Gang"));



    public foundersSword(){
        item = new ItemStack(itemMaterial, 1);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

}
