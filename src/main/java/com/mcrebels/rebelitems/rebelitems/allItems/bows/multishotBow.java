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

import java.math.BigDecimal;
import java.math.MathContext;
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
    private static int minArrowAmount = 1;
    private static int maxArrowAmount = 10;
    private static double minchance = 0.1;
    private static double maxchance = 1;
    private Integer arrowAmount;
    private Double chancePercent;
    private final NamespacedKey arrowAmountKey = new NamespacedKey(plugin, "arrowAmountKey");
    private final NamespacedKey arrowChanceKey = new NamespacedKey(plugin, "arrowChanceKey");



    public multishotBow(){
        itemName = MiniMessage.markdown().parse(plugin.getConfig().getString(configID+".displayname"));
        minArrowAmount = plugin.getConfig().getInt(configID+".minarrowamount");
        maxArrowAmount = plugin.getConfig().getInt(configID+".maxarrowamount");
        minchance = plugin.getConfig().getDouble(configID + ".minchance");
        maxchance = plugin.getConfig().getDouble(configID + ".maxchance");

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
                MiniMessage.markdown().parse("<yellow>Chance per Arrow: " + chanceInfo.getFirst() + "%"));
        tMeta.setCustomModelData(customMetaID);
        tMeta.getPersistentDataContainer().set(arrowAmountKey, PersistentDataType.INTEGER, arrowAmount);
        tMeta.getPersistentDataContainer().set(arrowChanceKey, PersistentDataType.DOUBLE, chancePercent);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
        return item;
    }

    public void checkBounds(Player player) {
        int currentNum = player.getInventory().getItemInMainHand().getItemMeta()
                .getPersistentDataContainer().get(arrowAmountKey, PersistentDataType.INTEGER);
        double currentChance = player.getInventory().getItemInMainHand().getItemMeta()
                .getPersistentDataContainer().get(arrowChanceKey, PersistentDataType.DOUBLE);
        boolean changeCount = false, changeChance = false, upCount = false, upChance = false;
        if(currentChance > maxchance) {
            changeChance = true;
            upChance = true;
        }
        else if(currentChance < minchance) {
            changeChance = true;
            upChance = false;
        }
        if(currentNum > maxArrowAmount) {
            changeCount = true;
            upCount = true;
        }
        else if(currentNum < minArrowAmount) {
            changeCount = true;
            upCount = false;
        }
        if(changeCount || changeChance) {
            updateChance(player.getInventory().getItemInMainHand(), changeCount, upCount, changeChance, upChance);
        }
    }

    private void updateChance(ItemStack customItem, boolean changeCount, boolean upCount, boolean changeChance, boolean upChance) {
        ItemMeta tMeta = customItem.getItemMeta();
        itemLore = tMeta.lore();
        double newChance = minchance;
        if(upChance) {
            newChance = maxchance;
        }
        BigDecimal bd = new BigDecimal(newChance * 100);
        bd = bd.round(new MathContext(getPrecision()));
        double displayChance = bd.doubleValue();
        String loreChance = "<#521717>" + displayChance;
        String loreCount = "<#521717>" + minArrowAmount;
        if(upCount) {
            loreCount = "<#ffc400>" + maxArrowAmount;
        }
        if(upChance) {
            loreChance = "<#ffc400>" + displayChance;
        }
        if(changeCount) {
            itemLore.set(3, MiniMessage.markdown().parse("<yellow>Max Extra Arrows: " + loreCount));
            if(upCount) {
                tMeta.getPersistentDataContainer().set(arrowAmountKey, PersistentDataType.INTEGER, maxArrowAmount);
            }
            else {
                tMeta.getPersistentDataContainer().set(arrowAmountKey, PersistentDataType.INTEGER, minArrowAmount);
            }
        }
        if(changeChance) {
            itemLore.set(4,MiniMessage.markdown().parse("<yellow>Chance per Arrow: " + loreChance + "%"));
            if(upChance) {
                tMeta.getPersistentDataContainer().set(arrowChanceKey, PersistentDataType.DOUBLE, maxchance);
            }
            else {
                tMeta.getPersistentDataContainer().set(arrowChanceKey, PersistentDataType.DOUBLE, minchance);
            }
        }
        tMeta.lore(itemLore);
        customItem.setItemMeta(tMeta);
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e ){
        if(e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if(metaCheck(player, customMetaID)){
                checkBounds(player);
                Integer arrowNum = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(arrowAmountKey, PersistentDataType.INTEGER);
                Double multiChance = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().get(arrowChanceKey, PersistentDataType.DOUBLE);
                for (int i = 0; i < arrowNum; i++) {
                    if (Math.random() < multiChance) {
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

