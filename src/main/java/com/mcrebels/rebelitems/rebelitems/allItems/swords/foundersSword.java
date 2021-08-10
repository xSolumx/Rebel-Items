package com.mcrebels.rebelitems.rebelitems.allItems.swords;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.allItems.Item;
import com.mcrebels.rebelitems.rebelitems.allItems.misc_Paper.currency;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.mcrebels.rebelitems.rebelitems.Utilities.metaCheck;

public class foundersSword extends Item implements Listener{
    private ItemStack item;

    private final Integer customMetaID = 2;
    private final String configID = "founderssword";
    private static Component itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>Sword of the Founder</gradient>");
    private final Material itemMaterial = Material.NETHERITE_SWORD;
    private final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    private static Integer mingold;
    private static Integer maxgold;

    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:green:blue>===================</gradient>"),
            MiniMessage.markdown().parse("<gradient:green:blue>Bestowed Upon The Rebel Founders</gradient>"),
            MiniMessage.markdown().parse("For Their Undying Commitment"),
            MiniMessage.markdown().parse("<blue>Pebble Gang"));



    public foundersSword(){
        itemName = MiniMessage.markdown().parse(plugin.getConfig().getString(configID+".displayname"));
        mingold = plugin.getConfig().getInt(configID+".mingold");
        maxgold = plugin.getConfig().getInt(configID+".maxgold");
    }

    @Override
    public String getName() {
        return "FoundersSword";
    }
    public ItemStack getItem(){
        item = new ItemStack(itemMaterial, 1);
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
        return item;
    }

    public void checkBounds(Player player) {
        //TODO with updateChance function
    }

    @EventHandler
    public void onKill(EntityDeathEvent e){
        if (e.getEntity().getKiller() != null){
            Player player = e.getEntity().getKiller();
            if (metaCheck(player, customMetaID)){
                e.getDrops().add(new currency((mingold + (int)(Math.random() * ((maxgold - mingold) + 1)))).getItem());
            }
        }
    }
}
