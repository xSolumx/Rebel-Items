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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
    private static int minLogs = 4;
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
            int logCount = b.getPlayer().getInventory().getItemInMainHand().getItemMeta()
                    .getPersistentDataContainer().get(logCountKey, PersistentDataType.INTEGER);
            if(logCount > maxLogs) {
                updateChance(maxLogs);
                logCount = maxLogs;
            }
            else if(logCount < minLogs) {
                updateChance(minLogs);
                logCount = minLogs;
            }
            Material material = b.getBlock().getType();
            if(isLog(material)) {
                Queue<Block> blocks = new LinkedList<>();
                blocks.add(b.getBlock());
                while(blocks.isEmpty() == false) {
                    --logCount;
                    Block currBlock = blocks.poll();
                    if(currBlock != b.getBlock()) {
                        currBlock.breakNaturally();
                    }
                    //Adding adjacent log blocks
                    if(isLog(currBlock.getRelative(BlockFace.NORTH).getType()) &&
                            !blocks.contains(currBlock.getRelative(BlockFace.NORTH))) {
                        blocks.add(currBlock.getRelative(BlockFace.NORTH));
                        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "North Added");
                    }
                    if(isLog(currBlock.getRelative(BlockFace.SOUTH).getType()) &&
                            !blocks.contains(currBlock.getRelative(BlockFace.SOUTH))) {
                        blocks.add(currBlock.getRelative(BlockFace.SOUTH));
                        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "South Added");

                    }
                    if(isLog(currBlock.getRelative(BlockFace.EAST).getType()) &&
                            !blocks.contains(currBlock.getRelative(BlockFace.EAST))) {
                        blocks.add(currBlock.getRelative(BlockFace.EAST));
                        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "East Added");

                    }
                    if(isLog(currBlock.getRelative(BlockFace.WEST).getType()) &&
                            !blocks.contains(currBlock.getRelative(BlockFace.WEST))) {
                        blocks.add(currBlock.getRelative(BlockFace.WEST));
                        getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "West Added");

                    }

                    if(logCount == 0) {
                        blocks.clear();
                        break;
                    }
                }
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

    private void updateChance(double newChance) {
        /*TODO: Update persistent data container in itemMeta
         * (idk how we'll get the item yet)
         * And update the item lore to display the new chance
         */
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
