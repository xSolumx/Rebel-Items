package com.mcrebels.rebelitems.rebelitems.allItems;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.SmithingInventory;
import org.jetbrains.annotations.NotNull;

public class RollItem implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            SmithingInventory gui = (SmithingInventory) Bukkit.createInventory(player, InventoryType.SMITHING);

            player.openInventory(gui);

        }
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.getInventory() instanceof SmithingInventory){
            if (!e.getSlotType().equals(InventoryType.SlotType.CONTAINER)){
                e.setCancelled(true);
            }
        }
    }
}
