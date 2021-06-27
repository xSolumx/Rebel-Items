package com.mcrebels.rebelitems.rebelitems;

import co.aikar.commands.MessageKeys;
import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import com.mcrebels.rebelitems.rebelitems.allItems.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RebelItems extends JavaPlugin {

    private static RebelItems plugin;
    private static PaperCommandManager commandManager;


    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        registerCommands();

        //load item event listeners
        getServer().getPluginManager().registerEvents(new vampireSword(), this);
        getServer().getPluginManager().registerEvents(new harpoon(), this);
        getServer().getPluginManager().registerEvents(new multishotBow(), this);
        getServer().getPluginManager().registerEvents(new chickenSword(), this);
        getServer().getPluginManager().registerEvents(new testItem(), this);
        getServer().getPluginManager().registerEvents(new foundersSword(), this);

        //loaded message
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "+++ RebelItems");
    }

    //item name index for command completions (idek how to handle this better)
    List<Item> allItems = Arrays.asList(
            new foundersSword(),
            new testItem(),
            new harpoon(),
            new chickenSword(),
            new multishotBow(),
            new vampireSword()
    );

    List<String> allItemsAsString = getAllItemsAsString();

    public List<String> getAllItemsAsString(){
        List<String> i = new ArrayList<>();
        for (Item j : allItems) {
           i.add(j.getName());
        }
        return i;
    }

    private void registerCommands(){
        commandManager = new PaperCommandManager(this);
        commandManager.enableUnstableAPI("brigadier");

        commandManager.getCommandCompletions().registerAsyncCompletion("items", c -> allItemsAsString);

        commandManager.registerCommand(new ItemCommands(this, allItemsAsString, allItems).setExceptionHandler((command, registeredCommand, sender, args, t) -> {
            sender.sendMessage(MessageType.ERROR, MessageKeys.ERROR_GENERIC_LOGGED);
            return true;
        }));


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "--- RebelItems");

    }

}
