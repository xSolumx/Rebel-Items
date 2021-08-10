package com.mcrebels.rebelitems.rebelitems.allItems;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Item {
    ItemStack item;

    public abstract String getName();

    public abstract void checkBounds(Player player);
    
    public ItemStack getItem(){
        return item;
    }

}
