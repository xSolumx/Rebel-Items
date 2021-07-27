package com.mcrebels.rebelitems.rebelitems.allItems.misc_Items;

import com.mcrebels.rebelitems.rebelitems.Pair;
import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

import static com.mcrebels.rebelitems.rebelitems.Utilities.generateRandom;
import static com.mcrebels.rebelitems.rebelitems.Utilities.metaCheck;
import static org.bukkit.Bukkit.getServer;

public class boostElytra extends Item implements Listener {
    private ItemStack item;
    private final Integer customMetaID = 12;
    private final String configID = "boostelytra";
    private final Component itemName;
    private final Material itemMaterial = Material.ELYTRA;
    private final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    private final NamespacedKey heightBoostKey = new NamespacedKey(plugin, "heightBoost");
    private List<Component> itemLore;
    private static double minHeight = .1;
    private static double maxHeight = .5;
    private double height;

    public boostElytra(){
        minHeight = plugin.getConfig().getDouble(configID + ".minboost");
        maxHeight = plugin.getConfig().getDouble(configID + ".maxboost");
        itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>" + plugin.getConfig().getString(configID + ".displayname") + "</gradient>");
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse(""));
    }

    @EventHandler
    private void onPlayerToggleFlightEvent(EntityToggleGlideEvent f){
        if(f.getEntity() instanceof Player) {
            Player player = (Player) f.getEntity();
            if(metaCheck(player, customMetaID) && isSneaking) {
                double heightBoost = player.getInventory().getChestplate().getItemMeta()
                        .getPersistentDataContainer().get(heightBoostKey, PersistentDataType.DOUBLE);
                Vector currentVel = player.getVelocity();
                currentVel.setY(currentVel.getY() + heightBoost);
                player.setVelocity(currentVel);
            }
        }
    }
    private boolean isSneaking = false;

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent e){
        isSneaking = e.isSneaking();
    }

    @Override
    public String getName() {
        return "BoostElytra";
    }

    public ItemStack getItem(){
        item = new ItemStack(itemMaterial, 1);
        Pair<String, Double> pair = generateRandom(minHeight, maxHeight);
        height = pair.getSecond();
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse("<gradient:yellow:blue>===================</gradient>"),
                MiniMessage.markdown().parse("<gradient:yellow:blue>Gives the user a boost when they launch!</gradient>"),
                MiniMessage.markdown().parse("<yellow>Velocity Boost: " + pair.getFirst()));
        ItemMeta tMeta = item.getItemMeta();
        tMeta.getPersistentDataContainer().set(heightBoostKey, PersistentDataType.DOUBLE, height);
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
        return item;
    }

    public void reRollItem() {
        //NEEDS UPDATING
        //chance = (Math.random() * (maxChance - minChance) + minChance);
        //chance = Math.floor(chance * 1000) / 1000;
        //updateChance(chance);
    }

    private void updateChance(double newChance) {
        /*TODO: Update persistent data container in itemMeta
         * (idk how we'll get the item yet)
         * And update the item lore to display the new chance
         */
    }


}

