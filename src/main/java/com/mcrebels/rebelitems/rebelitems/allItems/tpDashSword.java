package com.mcrebels.rebelitems.rebelitems.allItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class tpDashSword extends Item implements Listener {

    private ItemStack item;

    private final Integer customMetaID = 9;
    private final Component itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>Dash Sword</gradient>");
    private final Material itemMaterial = Material.NETHERITE_SWORD;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
            MiniMessage.markdown().parse("<gradient:green:blue>this is the first lore line</gradient>"),
            MiniMessage.markdown().parse("third line"),
            MiniMessage.markdown().parse("<blue>fourth line"));

    public tpDashSword(){
        item = new ItemStack(itemMaterial, 1);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    public ItemStack getItem(){
        return item;
    }

    @Override
    public String getName() {
        return "TP_Dash_Sword";
    }

    @EventHandler
    public void onDash(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == customMetaID){
                Player player = e.getPlayer();
                player.setVelocity(player.getVelocity().multiply(3));
            }
        }
    }
}
