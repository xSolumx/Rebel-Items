package com.mcrebels.rebelitems.rebelitems;

import co.aikar.commands.MessageKeys;
import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import com.mcrebels.rebelitems.rebelitems.allItems.*;
import com.mcrebels.rebelitems.rebelitems.allItems.axes.berserkerAxe;
import com.mcrebels.rebelitems.rebelitems.allItems.bows.multishotBow;
import com.mcrebels.rebelitems.rebelitems.allItems.misc_Paper.chickenBones;
import com.mcrebels.rebelitems.rebelitems.allItems.misc_Paper.currency;
import com.mcrebels.rebelitems.rebelitems.allItems.misc_Paper.token;
import com.mcrebels.rebelitems.rebelitems.allItems.pickaxes.hastePickaxe;
import com.mcrebels.rebelitems.rebelitems.allItems.pickaxes.randomPickaxe;
import com.mcrebels.rebelitems.rebelitems.allItems.swords.chickenSword;
import com.mcrebels.rebelitems.rebelitems.allItems.swords.foundersSword;
import com.mcrebels.rebelitems.rebelitems.allItems.swords.tpDashSword;
import com.mcrebels.rebelitems.rebelitems.allItems.swords.vampireSword;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RebelItems extends JavaPlugin {

    private static RebelItems plugin;
    private static PaperCommandManager commandManager;
    List<Item> allItems;
    List<String> allItemsAsString;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.saveDefaultConfig();

        //load item event listeners
        getServer().getPluginManager().registerEvents(new vampireSword(), this);
        getServer().getPluginManager().registerEvents(new harpoon(), this);
        getServer().getPluginManager().registerEvents(new multishotBow(), this);
        getServer().getPluginManager().registerEvents(new chickenSword(), this);
        getServer().getPluginManager().registerEvents(new testItem(), this);
        getServer().getPluginManager().registerEvents(new foundersSword(), this);
        getServer().getPluginManager().registerEvents(new berserkerAxe(), this);
        getServer().getPluginManager().registerEvents(new tpDashSword(), this);
        getServer().getPluginManager().registerEvents(new randomPickaxe(), this);
        getServer().getPluginManager().registerEvents(new hastePickaxe(), this);


        //item name index for command completions (idek how to handle this better)
        allItems = getAllItems();

        allItemsAsString = getAllItemsAsString();

        registerCommands();

        //loaded message
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "+++ RebelItems");
    }

    //item name index for command completions (idek how to handle this better)
    public List<Item> getAllItems() {
        List<Item> allItems = Arrays.asList(
                new foundersSword(),
                new testItem(),
                new harpoon(),
                new chickenSword(),
                new multishotBow(),
                new vampireSword(),
                new berserkerAxe(),
                new tpDashSword(),
                new chickenBones(),
                new currency(),
                new randomPickaxe(),
                new hastePickaxe(),
                new token()
        );
        return allItems;
    };

    public List<String> getAllItemsAsString(){
        List<String> i = new ArrayList<>();
        for (Item j : allItems) {
           i.add(j.getName());
        }
        java.util.Collections.sort(i);
        return i;
    }

    private void registerCommands(){
        commandManager = new PaperCommandManager(this);
        commandManager.enableUnstableAPI("brigadier");

        commandManager.getCommandCompletions().registerAsyncCompletion("items", c -> allItemsAsString);

        commandManager.registerCommand(new ItemCommands(this, allItemsAsString, getAllItems()).setExceptionHandler((command, registeredCommand, sender, args, t) -> {
            sender.sendMessage(MessageType.ERROR, MessageKeys.ERROR_GENERIC_LOGGED);
            return true;
        }));


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("rebelitems.reload") && label.equalsIgnoreCase("rebelitems:reload")) {
            this.saveDefaultConfig();
            this.reloadConfig();
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "RebelItems Reloaded");
            if(sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + "RebelItems Reloaded");
            }

            registerCommands();

            return false;
        }
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "--- RebelItems");

    }

}
