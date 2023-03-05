package com.mcrebels.rebelitems.rebelitems;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.mcrebels.rebelitems.rebelitems.Effects.Effect;
import com.mcrebels.rebelitems.rebelitems.allItems.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

@CommandAlias("rebelitem")
@Description("Base rebelitems commands")
public class ItemCommands extends BaseCommand{

    Plugin plugin;
    List<String> allItemsAsString;
    List<Item> allItems;
    public ItemCommands(Plugin p, List<String> items, List<Item> allItemsAsItem){
        plugin = p;
        allItemsAsString = items;
        allItems = allItemsAsItem;
    }

    public Item compareItems(String s){
        for (Item i : allItems
             ) {
            if (i.getName().equalsIgnoreCase(s)){
                return i;
            }
        }
        return null;
    }


    @CommandCompletion("@Players:30 @items")
    @Subcommand("give")
    public void onItem(OnlinePlayer player, String item){
        if (allItemsAsString.contains(item)){
            if ( compareItems(item) != null){
                player.getPlayer().getInventory().addItem(compareItems(item).getItem());
            }
        }
    }

    Set<Effect> allEffects = RebelItems.getAllEffects();
    @Subcommand("roll")
    public void rollItemInHand(CommandSender sender){
        if (sender instanceof Player){
            Player player = (Player) sender;
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.hasItemMeta()){
                ItemMeta m = item.getItemMeta();

                //See if this works... Getting the PDC for each effect
                Set<PersistentDataContainer> allPS = new HashSet<>();
                for (Effect e :
                        allEffects) {
                    allPS.add(e.getP());
                }

                PersistentDataContainer root = m.getPersistentDataContainer();
                for (NamespacedKey r :
                        root.getKeys()) {
                        if (r.getKey().contains("effects")) {
                            PersistentDataContainer sub = root.get(r, PersistentDataType.TAG_CONTAINER);
                            Bukkit.getLogger().log(Level.INFO,"Contains key: "+r.getKey() + "Val: " + r.value());

                            for (NamespacedKey n:
                                    sub.getKeys()) {
                                Bukkit.getLogger().log(Level.WARNING,"Contains key: "+n.getKey() + "Val: " + n.value());
                            }
                        }
                        else {
                            Bukkit.getLogger().log(Level.WARNING,"Try again");
                        }
                    }
            }
        }
    }

        // Command executed with desired parameters.
}
