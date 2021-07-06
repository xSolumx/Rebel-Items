package com.mcrebels.rebelitems.rebelitems.allItems;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.*;

import static com.mcrebels.rebelitems.rebelitems.Utilities.metaCheck;
import static org.bukkit.entity.AbstractArrow.PickupStatus.DISALLOWED;

public class multishotBow extends Item implements Listener {
    private ItemStack item;

    private final Integer customMetaID = 6;
    private final Component itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>Test Item Display Name</gradient>");
    private final Material itemMaterial = Material.BOW;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
            MiniMessage.markdown().parse("<gradient:green:blue>this is the first lore line</gradient>"),
            MiniMessage.markdown().parse("third line"),
            MiniMessage.markdown().parse("<blue>fourth line"));



    public multishotBow(){
        item = new ItemStack(itemMaterial, 1);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    @Override
    public String getName() {
        return "MultiShotBow";
    }

    public ItemStack getItem(){
        return item;
    }

    private int arrowAmount = 4;


    @EventHandler
    public void onShoot(EntityShootBowEvent e ){
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(metaCheck(player, customMetaID)){
                    for (int i = 0; i < arrowAmount; i++) {
                            player.launchProjectile(Arrow.class, getRandomArrow(e.getProjectile().getVelocity())).setPickupStatus(DISALLOWED);
                    }
                }
            }
        }

    public Vector getRandomArrow(Vector arrVec){
         return new Vector((arrVec.getX()+getRand()),(arrVec.getY()+getRand()),(arrVec.getZ()+(getRand())));

    }
    public double getRand(){
        Random random = new Random();
        double r = (random.nextDouble() * 2 - 1);
        if (r < 1){
            r = r*-0.2;
        }
        if(r > 1){
            r = r*0.2;
        }
        return r;
    }
}

