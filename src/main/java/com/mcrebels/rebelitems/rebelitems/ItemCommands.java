package com.mcrebels.rebelitems.rebelitems;

import co.aikar.commands.BaseCommand;
import com.mcrebels.rebelitems.rebelitems.allItems.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ItemCommands extends BaseCommand implements CommandExecutor {

    Plugin plugin;

    List<Item> allItems =  Arrays.asList(
            new foundersSword(),
            new testItem(),
            new harpoon(),
            new chickenSword()
    );

    public ItemCommands(Plugin p){
        plugin = p;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            player.getInventory().addItem(new harpoon().getItem());
        }
        return false;
    }
}
