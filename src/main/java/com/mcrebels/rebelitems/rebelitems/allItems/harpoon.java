package com.mcrebels.rebelitems.rebelitems.allItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

import static com.mcrebels.rebelitems.rebelitems.Utilities.metaCheck;

public class harpoon extends Item implements Listener {
    private ItemStack item;

    private final Integer customMetaID = 5;
    private final Component itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>Harpoon</gradient>");
    private final Material itemMaterial = Material.CROSSBOW;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
            MiniMessage.markdown().parse("<gradient:green:blue>Bestowed Upon The Rebel Founders</gradient>"),
            MiniMessage.markdown().parse("For Their Undying Commitment"),
            MiniMessage.markdown().parse("<blue>Pebble Gang"));



    public harpoon(){
        item = new ItemStack(itemMaterial, 1);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    @EventHandler
    public void onHarpoonHit(ProjectileHitEvent e){
        if(e.getHitEntity()!=null) {
            if (e.getEntity().getShooter() instanceof Player) {
                if (metaCheck((Player) e.getEntity().getShooter(), customMetaID)) {
                    Player player = (Player) e.getEntity().getShooter();
                    Vector pos = e.getHitEntity().getLocation().toVector();
                    Vector target = player.getLocation().toVector();
                    Vector velocity = target.subtract(pos);

                    //
                    e.getHitEntity().setVelocity(velocity.normalize().multiply(((player.getLocation().distance(e.getHitEntity().getLocation())) / 1.5))); //change the number to change the mob's velocity

                    e.getHitEntity().getLastDamageCause().setDamage((e.getHitEntity().getLastDamageCause().getDamage() / 2)); //item deals half damage
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Harpoon";
    }

    public ItemStack getItem(){
        return item;
    }


}
