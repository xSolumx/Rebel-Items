package com.mcrebels.rebelitems.rebelitems.allItems;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.allItems.misc.chickenBones;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class chickenSword extends Item implements Listener {
    private ItemStack item;

    private final Integer customMetaID = 3;
    private final Component itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>Chicken Lord's Saber</gradient>");
    private final Material itemMaterial = Material.NETHERITE_SWORD;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
            MiniMessage.markdown().parse("<gradient:green:blue>From the treasury of the Chicken Lord himself</gradient>"),
            MiniMessage.markdown().parse("Consumes chicken bones to smite your enemies"),
            MiniMessage.markdown().parse("<blue>line 4"));



    public chickenSword(){
        item = new ItemStack(itemMaterial, 1);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    @EventHandler
    private void onChickenKill(EntityDeathEvent e){
        if(e.getEntityType() == EntityType.CHICKEN){
            if (e.getEntity().getKiller() != null){
                Player player = e.getEntity().getKiller();
                if (player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 3){
                    e.getDrops().add(new chickenBones((int) (Math.random()*4)).getItem());
                }
            }
        }
    }

    @EventHandler
    private void onEntityHit(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            if (((Player) e.getDamager()).getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 3){
                Player p = (Player) e.getDamager();
                if (p.getInventory().getItemInOffHand().equals(new chickenBones().getItem())){
                    //damage logic here
                    if(e.getEntity().isDead()){
                        //summons a chicken when the entity attacked dies REQUIRES TESTING
                    e.getEntity().getLocation().getWorld().spawn(e.getEntity().getLocation(), EntityType.CHICKEN.getEntityClass());
                    }


                    p.getInventory().getItemInOffHand().setAmount(p.getInventory().getItemInOffHand().getAmount()-1);
                }
            }
        }
    }
}
