package com.mcrebels.rebelitems.rebelitems.allItems.pickaxes;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import com.sun.tools.javac.util.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

import static com.mcrebels.rebelitems.rebelitems.Utilities.generateRandom;
import static com.mcrebels.rebelitems.rebelitems.Utilities.metaCheck;

public class hastePickaxe extends Item implements Listener {
    private ItemStack item;
    private final Integer customMetaID = 11;
    private final String configID = "hastepickaxe";
    private final Component itemName;
    private final Material itemMaterial = Material.NETHERITE_PICKAXE;
    private final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    private final NamespacedKey hasteChanceKey = new NamespacedKey(plugin, "hasteChance");
    private List<Component> itemLore;
    private static double minChance = .1;
    private static double maxChance = .5;
    private static int hasteLevel = 1;
    private static int hasteDuration = 10;
    private double chance;

    public hastePickaxe(){
        minChance = plugin.getConfig().getDouble(configID + ".minchance");
        maxChance = plugin.getConfig().getDouble(configID + ".maxchance");
        hasteLevel = plugin.getConfig().getInt(configID + ".hastelevel");
        hasteDuration = plugin.getConfig().getInt(configID + ".hasteduration");
        itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>" + plugin.getConfig().getString(configID + ".displayname") + "</gradient>");
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse(""));
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent b){
        if(metaCheck(b.getPlayer(), customMetaID)) {
            double hasteChance = b.getPlayer().getInventory().getItemInMainHand().getItemMeta()
                    .getPersistentDataContainer().get(hasteChanceKey, PersistentDataType.DOUBLE);
            if(hasteChance > maxChance) {
                updateChance(maxChance);
                hasteChance = maxChance;
            }
            else if(hasteChance < minChance) {
                updateChance(minChance);
                hasteChance = minChance;
            }
            if(Math.random() < hasteChance) {
                Player player = b.getPlayer();
                PotionEffect effect = new PotionEffect(PotionEffectType.FAST_DIGGING, hasteDuration * 20, hasteLevel - 1);
                player.addPotionEffect(effect);
            }
        }
    }

    @Override
    public String getName() {
        return "HastePickaxe";
    }

    public ItemStack getItem(){
        item = new ItemStack(itemMaterial, 1);
        Pair<String, Double> pair = generateRandom(minChance, maxChance);
        chance = pair.snd;
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse("<gradient:yellow:blue>===================</gradient>"),
                MiniMessage.markdown().parse("<gradient:yellow:blue>Occasionally allows the user to mine faster!</gradient>"),
                MiniMessage.markdown().parse("<yellow>Haste chance: " + pair.fst));
        ItemMeta tMeta = item.getItemMeta();
        tMeta.getPersistentDataContainer().set(hasteChanceKey, PersistentDataType.DOUBLE, chance);
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
        return item;
    }

    public void reRollItem() {
        //NEEDS UPDATING
        chance = (Math.random() * (maxChance - minChance) + minChance);
        chance = Math.floor(chance * 1000) / 1000;
        updateChance(chance);
    }

    private void updateChance(double newChance) {
        /*TODO: Update persistent data container in itemMeta
        * (idk how we'll get the item yet)
        * And update the item lore to display the new chance
        */
    }


}
