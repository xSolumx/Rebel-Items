package com.mcrebels.rebelitems.rebelitems;

import com.mcrebels.additionalblocks.additionalblocks.Items.ItemBuilder;
import com.sk89q.worldedit.event.platform.BlockInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;
import java.util.logging.Level;

import static com.mcrebels.additionalblocks.additionalblocks.Items.ItemHandler.isCustomItem;
import static com.mcrebels.rebelitems.rebelitems.RebelItems.LogToConsole;

public class EnhanceItem implements Listener {

    private EnhancementInventory inv = (EnhancementInventory) Bukkit.createInventory(null, InventoryType.SMITHING, "Enhancement inv");

    public ItemStack Enhance(Player player, ItemStack item){
        return item;
    }

    @EventHandler
    public void anvilClickEvent(PlayerInteractEvent e){
        if (e.getClickedBlock() != null){
            //check if item is a custom anvil
            if (e.getClickedBlock().getType() == Material.BARRIER){
                Location location = e.getClickedBlock().getLocation().toCenterLocation();
                if (!location.getNearbyEntitiesByType(ArmorStand.class,1).isEmpty()){
                    location.getNearbyEntitiesByType(ArmorStand.class,1).forEach( i -> {
                       if (isCustomItem(i.getEquipment().getHelmet())){
                           if (i.getEquipment().getHelmet().getItemMeta().getCustomModelData() == 9){

                                e.getPlayer().openInventory(inv);
                           }
                       }
                    });
                    Bukkit.getLogger().log(Level.INFO,"removed custom block");
                }
            }
        }
    }

    @EventHandler
    public void itemPlaceEvent(InventoryClickEvent e){
        if (e.getInventory().getType() == InventoryType.SMITHING) {
            if (e.getInventory().equals(inv)) {
                SmithingInventory Inv = (SmithingInventory) e.getInventory();
                ItemStack inputI = Inv.getInputEquipment();
                ItemStack inputM = Inv.getInputMineral();

                if(isCustomItem(inputI) && isCustomItem(inputM)){
                    //idk what to do yet
                e.setCancelled(false);

                }else {
                    e.setCancelled(true);
                }
            }
            //check if inputs match


            //
        }
    }



    //OUTDATED for new storage container
    public void getValue(ItemStack item){
        Set<NamespacedKey> keyList = item.getItemMeta().getPersistentDataContainer().getKeys();
        for (NamespacedKey k :
                keyList) {
            String key = k.getKey();
            String val = k.value();
            ItemBuilder ib = new ItemBuilder(item);
            String t = ib.getCustomTag(k, PersistentDataType.STRING);
            LogToConsole(t);
            LogToConsole(key+" : "+val);
        }
    }
}
