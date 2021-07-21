package com.mcrebels.rebelitems.rebelitems.allItems.swords;

import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import com.mcrebels.rebelitems.rebelitems.RebelItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

import static com.mcrebels.rebelitems.rebelitems.Utilities.metaCheck;

public class vampireSword extends Item implements Listener {

    private ItemStack item;

    private final Integer customMetaID = 7;
    private final String configID = "vampiresword";
    private final Component itemName;
    private final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    private final Material itemMaterial = Material.NETHERITE_SWORD;
    private final NamespacedKey leechPercentKey = new NamespacedKey(plugin, "leechPercent");
    private List<Component> itemLore;
    private static double minPercent = .02;
    private static double maxPercent = .1;
    private double percent;



    public vampireSword(){
        minPercent = plugin.getConfig().getDouble(configID + ".minpercent");
        maxPercent = plugin.getConfig().getDouble(configID + ".maxpercent");
        itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>" + plugin.getConfig().getString(configID + ".displayname") + "</gradient>");
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse(""));
    }

    @Override
    public String getName() {
        return "VampireSword";
    }

    public ItemStack getItem(){
        item = new ItemStack(itemMaterial, 1);
        percent = (Math.random() * (maxPercent - minPercent) + minPercent);
        percent = Math.floor(percent * 1000) / 1000;
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
                MiniMessage.markdown().parse("<gradient:green:blue>Grants the user a small amount of </gradient>"),
                MiniMessage.markdown().parse("<gradient:green:blue>damage dealt as health</gradient>"),
                MiniMessage.markdown().parse("<gradient:green:blue>Leech amount: " + percent * 100 + "% of damage</gradient>"));
        ItemMeta tMeta = item.getItemMeta();
        tMeta.getPersistentDataContainer().set(leechPercentKey, PersistentDataType.DOUBLE, percent);
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
        return item;
    }


    @EventHandler
    public void onSwordAttack(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            if(metaCheck(player, customMetaID)){
                double damageDone = e.getDamage();
                double leechPercent = player.getInventory().getItemInMainHand().getItemMeta()
                        .getPersistentDataContainer().get(leechPercentKey, PersistentDataType.DOUBLE);
                double lifeStealValue = damageDone * leechPercent;
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
