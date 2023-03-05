package com.mcrebels.rebelitems.rebelitems.allItems.swords;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class VampireSwordConcept extends Item {

    private final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    private ItemStack s = new ItemStack(Material.NETHERITE_SWORD,1);


    @Override
    public String getName() {
        return null;
    }

    @Override
    public void checkBounds(Player player) {

    }
}
