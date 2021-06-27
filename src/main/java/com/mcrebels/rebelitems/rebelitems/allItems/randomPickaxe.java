package com.mcrebels.rebelitems.rebelitems.allItems;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.allItems.misc.chickenBones;
import com.sk89q.worldedit.world.registry.BlockMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class randomPickaxe extends Item implements Listener {
    private ItemStack item;

    private final Integer customMetaID = 8;
    private final Component itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>Gambler's Pickaxe</gradient>");
    private final Material itemMaterial = Material.NETHERITE_PICKAXE;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:yellow:blue>===================</gradient>"),
            MiniMessage.markdown().parse("<gradient:yellow:blue>From the treasury of the Chicken Lord himself</gradient>"),
            MiniMessage.markdown().parse("Occasionally gives the user a random minecraft block in"),
            MiniMessage.markdown().parse("addition to the broken block."));



    public randomPickaxe(){
        item = new ItemStack(itemMaterial, 1);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent b){
        if(b.getPlayer() != null &&
                b.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                b.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 8) {
            //Rudimentary way to make a 25% chance (will be lower later)
            //int yes = (int)(Math.random() * 4); <-- Will use for probability later
            if(true) {
                Player player = b.getPlayer();
                Location dropsLoc = b.getBlock().getLocation();
                Material[] list = Material.values();
                Material randomMaterial;
                do {
                    randomMaterial = list[(int)(Math.random() * list.length)];
                }while(randomMaterial.isBlock() == false);
                player.getWorld().dropItem(dropsLoc, new ItemStack(randomMaterial, 1));
            }
        }
    }

    @Override
    public String getName() {
        return "GamblerPickaxe";
    }

    public ItemStack getItem(){
        return item;
    }
}
