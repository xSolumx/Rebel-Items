package com.mcrebels.rebelitems.rebelitems;

import co.aikar.commands.PaperCommandManager;
import com.mcrebels.rebelitems.rebelitems.allItems.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class RebelItems extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        //load item event listeners
        getServer().getPluginManager().registerEvents(new vampireSword(), this);
        getServer().getPluginManager().registerEvents(new harpoon(), this);
        getServer().getPluginManager().registerEvents(new multishotBow(), this);
        getServer().getPluginManager().registerEvents(new chickenSword(), this);
        getServer().getPluginManager().registerEvents(new testItem(), this);

        this.getCommand("rebelitemgive").setExecutor(new ItemCommands(this));
        this.getCommand("showgui").setExecutor(new testGUI());

        //for acf, not properly implemented yet
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new ItemCommands(this));

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "+++ RebelItems");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "--- RebelItems");

    }

}
