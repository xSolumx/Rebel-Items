package com.mcrebels.rebelitems.rebelitems;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import com.mcrebels.rebelitems.rebelitems.allItems.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.List;

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

        // Command executed with desired parameters.
}
