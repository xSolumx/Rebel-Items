package com.mcrebels.rebelitems.rebelitems.allItems.axes;

import com.mcrebels.rebelitems.rebelitems.Pair;
import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static com.mcrebels.rebelitems.rebelitems.Utilities.*;
import static org.bukkit.Bukkit.getServer;

public class treefellerAxe extends Item implements Listener {
    private ItemStack item;
    private final Integer customMetaID = 20;
    private final String configID = "treefelleraxe";
    private final Component itemName;
    private final Material itemMaterial = Material.NETHERITE_AXE;
    private final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    private final NamespacedKey logCountKey = new NamespacedKey(plugin, "logCount");
    private List<Component> itemLore;
    private static int minLogs = 3;
    private static int maxLogs = 8;
    private int numLogs;

    public treefellerAxe(){
        minLogs = plugin.getConfig().getInt(configID + ".minlogs");
        maxLogs = plugin.getConfig().getInt(configID + ".maxlogs");
        itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>" + plugin.getConfig().getString(configID + ".displayname") + "</gradient>");
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse(""));
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent b){
        if(metaCheck(b.getPlayer(), customMetaID)) {
            int brokenCount = 0;
            int logCount = b.getPlayer().getInventory().getItemInMainHand().getItemMeta()
                    .getPersistentDataContainer().get(logCountKey, PersistentDataType.INTEGER);
            if(logCount > maxLogs) {
                updateChance(b.getPlayer().getInventory().getItemInMainHand(), maxLogs, true);
                logCount = maxLogs;
            }
            else if(logCount < minLogs) {
                updateChance(b.getPlayer().getInventory().getItemInMainHand(), minLogs, false);
                logCount = minLogs;
            }
            Material material = b.getBlock().getType();
            if(isLog(material)) {
                Queue<Block> blocks = new LinkedList<>();
                blocks.add(b.getBlock());
                while(blocks.isEmpty() == false) {
                    ++brokenCount;
                    --logCount;
                    Block currBlock = blocks.peek();
                    currBlock.breakNaturally();
                    //Adding adjacent log blocks with blockFace values
                    for(int face = 0; face < BlockFace.values().length; ++face) {
                        if(isLog(currBlock.getRelative(BlockFace.values()[face]).getType()) &&
                                !blocks.contains(currBlock.getRelative(BlockFace.values()[face])) &&
                                currBlock.getRelative(BlockFace.values()[face]) != b.getBlock()) {
                            blocks.add(currBlock.getRelative(BlockFace.values()[face]));
                        }
                    }
                    //Adding remaining adjacent log blocks
                    int x = 0, y = 0, z = 0;
                    for(int diag = 0; diag < 16; ++diag) {
                        if(diag == 0) { x = 1; y = 1 ; z = 0; }
                        else if(diag == 1) { x = 1; y = 1 ; z = 1; }
                        else if(diag == 2) { x = 0; y = 1 ; z = 1; }
                        else if(diag == 3) { x = -1; y = 1 ; z = 0; }
                        else if(diag == 4) { x = -1; y = 1 ; z = 1; }
                        else if(diag == 5) { x = -1; y = 1 ; z = -1; }
                        else if(diag == 6) { x = 0; y = 1 ; z = -1; }
                        else if(diag == 7) { x = 1; y = 1 ; z = -1; }
                        else if(diag == 8) { x = 1; y = -1 ; z = 0; }
                        else if(diag == 9) { x = 1; y =-1 ; z = 1; }
                        else if(diag == 10) { x = 0; y = -1 ; z = 1; }
                        else if(diag == 11) { x = -1; y = -1 ; z = 0; }
                        else if(diag == 12) { x = -1; y = -1 ; z = 1; }
                        else if(diag == 13) { x = -1; y = -1 ; z = -1; }
                        else if(diag == 14) { x = 0; y = -1 ; z = -1; }
                        else if(diag == 15) { x = 1; y = -1 ; z = -1; }
                        if(isLog(currBlock.getRelative(x, y, z).getType()) &&
                                !blocks.contains(currBlock.getRelative(x, y, z)) &&
                                currBlock.getRelative(x, y, z) != b.getBlock()) {
                            blocks.add(currBlock.getRelative(x, y, z));
                        }
                    }
                    blocks.remove();
                    if(logCount == 0) {
                        blocks.clear();
                        break;
                    }
                }
                ItemStack tool = b.getPlayer().getInventory().getItemInMainHand();
                Damageable toolDurability = (Damageable) tool.getItemMeta();
                if(tool.containsEnchantment(Enchantment.DURABILITY)) {
                    double unbreakingChance = 1.00 / tool.getEnchantmentLevel(Enchantment.DURABILITY);
                    for(int z = 0; z < brokenCount; ++z) {
                        if(Math.random() < unbreakingChance) {
                            toolDurability.setDamage(toolDurability.getDamage() + 1);
                        }
                    }
                }
                else {
                    toolDurability.setDamage(toolDurability.getDamage() + brokenCount);
                }
                tool.setItemMeta((ItemMeta)toolDurability);
            }
        }
    }

    @Override
    public String getName() {
        return "TreefellerAxe";
    }

    public ItemStack getItem(){
        item = new ItemStack(itemMaterial, 1);
        Pair<String, Integer> pair = generateRandomInt(minLogs, maxLogs);
        numLogs = pair.getSecond();
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse("<gradient:yellow:blue>===================</gradient>"),
                MiniMessage.markdown().parse("<gradient:yellow:blue>Breaks multiple logs on each use!</gradient>"),
                MiniMessage.markdown().parse("<yellow>Extra Log Amount: " + pair.getFirst()));
        ItemMeta tMeta = item.getItemMeta();
        tMeta.getPersistentDataContainer().set(logCountKey, PersistentDataType.INTEGER, numLogs);
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
        return item;
    }

    public void reRollItem() {
        //Maybe DODO
    }

    public void checkBounds(Player player) {
        int currentCount = player.getInventory().getItemInMainHand().getItemMeta()
                .getPersistentDataContainer().get(logCountKey, PersistentDataType.INTEGER);
        if(currentCount > maxLogs) {
            updateChance(player.getInventory().getItemInMainHand(), maxLogs, true);
        }
        else if(currentCount < minLogs) {
            updateChance(player.getInventory().getItemInMainHand(), minLogs, false);
        }
    }

    private void updateChance(ItemStack customItem, int newCount, boolean upBound) {
        String loreValue = "<#521717>" + newCount;
        if(upBound) {
            loreValue = "<#ffc400>" + newCount;
        }
        itemLore = Arrays.asList(
                MiniMessage.markdown().parse("<gradient:yellow:blue>===================</gradient>"),
                MiniMessage.markdown().parse("<gradient:yellow:blue>Breaks multiple logs on each use!</gradient>"),
                MiniMessage.markdown().parse("<yellow>Extra Log Amount: " + loreValue));
        ItemMeta tMeta = customItem.getItemMeta();
        tMeta.getPersistentDataContainer().set(logCountKey, PersistentDataType.INTEGER, newCount);
        tMeta.lore(itemLore);
        customItem.setItemMeta(tMeta);
    }

    private boolean isLog(Material material) {
        List<Material> woodTypes = Arrays.asList(Material.ACACIA_LOG, Material.OAK_LOG, Material.DARK_OAK_LOG,
                Material.JUNGLE_LOG, Material.BIRCH_LOG, Material.SPRUCE_LOG, Material.WARPED_STEM, Material.CRIMSON_STEM);
        for(int a = 0; a < woodTypes.size(); ++a) {
            if(woodTypes.get(a) == material) {
                return true;
            }
        }
        return false;
    }


}
