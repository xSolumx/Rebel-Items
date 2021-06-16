package com.mcrebels.rebelitems.rebelitems.allItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class vampireSword extends Item implements Listener {

    private ItemStack item;

    private final Integer customMetaID = 7;
    private final Component itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>Vampire Sworde</gradient>");
    private final Material itemMaterial = Material.DIAMOND_SWORD;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
            MiniMessage.markdown().parse("<gradient:green:blue>this is the first lore line</gradient>"),
            MiniMessage.markdown().parse("third line"),
            MiniMessage.markdown().parse("<blue>fourth line"));



    public vampireSword(){
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


    @EventHandler
    public void onSwordAttack(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            if (player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 7){
                double damageDone = e.getDamage();
                double lifeStealValue = damageDone*0.1;
                if(player.getHealth()+lifeStealValue >= 20){
                    player.setHealth(20);
                }
                else {
                    player.setHealth(player.getHealth() + lifeStealValue);
                }
            }
        }
    }
}
