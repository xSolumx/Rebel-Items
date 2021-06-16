package com.mcrebels.rebelitems.rebelitems;

import co.aikar.commands.PaperCommandManager;
import com.mcrebels.rebelitems.rebelitems.allItems.testItem;
import com.mcrebels.rebelitems.rebelitems.allItems.harpoon;
import com.mcrebels.rebelitems.rebelitems.allItems.testGUI;
import org.bukkit.plugin.java.JavaPlugin;

public final class RebelItems extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new testItem(), this);
        getServer().getPluginManager().registerEvents(new harpoon(), this);
        this.getCommand("rebelitemgive").setExecutor(new ItemCommands(this));
        this.getCommand("showgui").setExecutor(new testGUI());

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new ItemCommands(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
