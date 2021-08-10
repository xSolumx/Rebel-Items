package com.mcrebels.rebelitems.rebelitems.allItems.bows;

import com.mcrebels.rebelitems.rebelitems.Pair;
import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.*;

import static com.mcrebels.rebelitems.rebelitems.Utilities.*;
import static org.bukkit.entity.AbstractArrow.PickupStatus.DISALLOWED;

public class multishotBow extends Item implements Listener {
    private ItemStack item;
    private final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    private final Integer customMetaID = 6;
    private final String configID = "multishotbow";
    private final Component itemName;
    private final Material itemMaterial = Material.BOW;
    private List<Component> itemLore;
    private int minArrowAmount=1;
    private int maxArrowAmount=10;
    private double minchance=0.1;
    private double maxchance = 1;
    private Integer arrowAmount;
    private Double chancePercent;
    private final NamespacedKey arrowAmountKey = new NamespacedKey(plugin, "arrowAmountKey");
    private final NamespacedKey arrowChanceKey = new NamespacedKey(plugin, "arrowChanceKey");



    public multishotBow(){
        itemName = MiniMessage.markdown().parse(plugin.getConfig().getString(configID+".displayname"));
        minArrowAmount = plugin.getConfig().getInt(configID+".minarrowamount");
        maxArrowAmount = plugin.getConfig().getInt(configID+".maxarrowamount");
    }

    @Override
    public String getName() {
        return "MultiShotBow";
    }

    public ItemStack getItem(){
        item = new ItemStack(itemMaterial, 1);
        Pair<String, Integer> randomInfo = generateRandomInt(minArrowAmount, maxArrowAmount);
        Pair<String, Double> chanceInfo = generateRandom(minchance, maxchance);
        chancePercent = chanceInfo.getSecond();
        arrowAmount = randomInfo.getSecond();
        ItemMeta tMeta = item.getItemMeta();
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
                MiniMessage.markdown().parse("<gradient:green:blue>Grants the user a small amount of </gradient>"),
                MiniMessage.markdown().parse("<gradient:green:blue>damage dealt as health</gradient>"),
                MiniMessage.markdown().parse("<yellow>Max Extra Arrows: " + randomInfo.getFirst()),
                MiniMessage.markdown().parse("<yellow>Chance: " + chanceInfo.getFirst() + "%"));
        tMeta.setCustomModelData(customMetaID);
        tMeta.getPersistentDataContainer().set(arrowAmountKey, PersistentDataType.INTEGER, arrowAmount);
        tMeta.getPersistentDataContainer().set(arrowChanceKey, PersistentDataType.DOUBLE, chancePercent);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
        return item;
    }

    public void checkBounds(Player player) {
        //TODO with updateChance function
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e ){
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(metaCheck(player, customMetaID)){
                Integer a = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(arrowAmountKey, PersistentDataType.INTEGER);
                Double c = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(arrowChanceKey, PersistentDataType.DOUBLE);
                for (int i = 0; i < a; i++) {
                    if (Math.random() < c) {
                        player.launchProjectile(Arrow.class, getRandomArrow(e.getProjectile().getVelocity())).setPickupStatus(DISALLOWED);
                    }
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

