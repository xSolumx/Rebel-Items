package com.mcrebels.rebelitems.rebelitems.Effects;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public interface Effect extends Listener {
    Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    Economy eco = RebelItems.getEconomy();

    PersistentDataContainer p = null;

    public default PersistentDataContainer getP(){
        return p;
    }


}
