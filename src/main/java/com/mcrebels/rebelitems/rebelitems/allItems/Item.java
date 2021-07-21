package com.mcrebels.rebelitems.rebelitems.allItems;

import org.bukkit.inventory.ItemStack;

public abstract class Item {
    ItemStack item;

    public abstract String getName();

    public ItemStack getItem(){
        return item;
    }


}
