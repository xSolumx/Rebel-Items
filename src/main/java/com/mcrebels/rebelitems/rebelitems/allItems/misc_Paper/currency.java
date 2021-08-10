package com.mcrebels.rebelitems.rebelitems.allItems.misc_Paper;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

import static com.mcrebels.rebelitems.rebelitems.Utilities.metaCheck;

public class currency extends Item implements Listener {

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

    public void checkBounds(Player player) {
        //TODO with updateChance function
    }

    @Override
    public String getName() {
        return "Currency";
    }

    public ItemStack getItem()
    {
        return item;
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e){
        if (e.getEntity() instanceof Player){
            if (e.getItem().getItemStack().hasItemMeta()){
                if (e.getItem().getItemStack().getItemMeta().hasCustomModelData()){
                    if (e.getItem().getItemStack().getItemMeta().getCustomModelData() == customMetaID){
                        Player player = (Player)e.getEntity();
                        EconomyResponse r = RebelItems.getEconomy().depositPlayer(player, e.getItem().getItemStack().getAmount());
                        e.setCancelled(true);
                        e.getItem().remove();
                        if(r.transactionSuccess()) {
                            player.sendMessage(String.format("+ %s", RebelItems.getEconomy().format(r.amount), RebelItems.getEconomy().format(r.balance)));
                        } else {
                            player.sendMessage(String.format("An error occured: %s", r.errorMessage));
                        }

                    }
                }
            }
        }
    }
}