package com.mcrebels.rebelitems.rebelitems.allItems.axes;

import com.mcrebels.rebelitems.rebelitems.allItems.Item;
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

import static com.mcrebels.rebelitems.rebelitems.Utilities.metaCheck;

public class berserkerAxe extends Item implements Listener {

    private ItemStack item;

    private final Integer customMetaID = 8;
    private final Component itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>Berserker Axe</gradient>");
    private final Material itemMaterial = Material.NETHERITE_AXE;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
            MiniMessage.markdown().parse("<gradient:green:blue>this is the first lore line</gradient>"),
            MiniMessage.markdown().parse("third line"),
            MiniMessage.markdown().parse("<blue>fourth line"));



    public berserkerAxe(){
        item = new ItemStack(itemMaterial, 1);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    @Override
    public String getName() {
        return "BerserkerAxe";
    }
    public ItemStack getItem(){
        return item;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            if (metaCheck(player,customMetaID)){
                double damage = e.getDamage()+(e.getDamage()/player.getHealth());
                e.setDamage(damage);
                player.sendMessage(String.valueOf(damage));
            }
        }
    }
}
