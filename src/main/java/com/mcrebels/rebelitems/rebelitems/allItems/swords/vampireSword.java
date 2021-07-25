package com.mcrebels.rebelitems.rebelitems.allItems.swords;

import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import com.mcrebels.rebelitems.rebelitems.RebelItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.math.RandomUtils;
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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.mcrebels.rebelitems.rebelitems.Utilities.getWorthColour;
import static com.mcrebels.rebelitems.rebelitems.Utilities.metaCheck;
import static org.apache.commons.lang.math.RandomUtils.nextDouble;
import static org.bukkit.Bukkit.getServer;

public class vampireSword extends Item implements Listener {

    private ItemStack item;

    private final Integer customMetaID = 7;
    private final String configID = "vampiresword";
    private final Component itemName;
    private final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    private final Material itemMaterial = Material.NETHERITE_SWORD;
    private final NamespacedKey leechPercentKey = new NamespacedKey(plugin, "leechPercent");
    private List<Component> itemLore;
    private static double minPercent = -0.05;
    private static double maxPercent = 0.1;
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

        //useless code for bug testing aand shit
        DecimalFormat fmt = new DecimalFormat("#.#");
        fmt.setRoundingMode(RoundingMode.HALF_UP);
        getServer().getConsoleSender().sendMessage(""+percent);
        getServer().getConsoleSender().sendMessage(fmt.format(percent*100));
        //ends here

        itemLore = Arrays.asList(
                MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
                MiniMessage.markdown().parse("<gradient:green:blue>Grants the user a small amount of </gradient>"),
                MiniMessage.markdown().parse("<gradient:green:blue>damage dealt as health</gradient>"),
                MiniMessage.markdown().parse("<blue>Leech amount: " + getWorthColour(minPercent, maxPercent, percent)));
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
