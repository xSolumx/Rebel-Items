package com.mcrebels.rebelitems.rebelitems;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingInventory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public interface EnhancementInventory extends SmithingInventory {
    @Nullable
    ItemStack getResult();

    void setResult(@Nullable ItemStack var1);

    @Nullable
    Recipe getRecipe();

    @Nullable
    default ItemStack getInputEquipment() {
        return this.getItem(0);
    }

    default void setInputEquipment(@Nullable ItemStack itemStack) {
        this.setItem(0, itemStack);
    }

    @Nullable
    default ItemStack getInputMineral() {
        return this.getItem(1);
    }

    default void setInputMineral(@Nullable ItemStack itemStack) {
        this.setItem(1, itemStack);
    }

}
