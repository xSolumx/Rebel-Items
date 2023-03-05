package com.mcrebels.rebelitems.rebelitems;

import co.aikar.commands.MessageKeys;
import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import com.mcrebels.rebelitems.rebelitems.Effects.*;
import com.mcrebels.rebelitems.rebelitems.allItems.*;
import com.mcrebels.rebelitems.rebelitems.allItems.swords.*;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RebelItems extends JavaPlugin {

    private static RebelItems plugin;
    private static PaperCommandManager commandManager;

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    List<NamespacedKey> allKeys = new ArrayList<>();

    List<Item> allItems;
    List<String> allItemsAsString;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.saveDefaultConfig();

        //vault support
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();

        //load item event listeners
        getServer().getPluginManager().registerEvents(new midas(), this);
        getServer().getPluginManager().registerEvents(new ice(),this);
        getServer().getPluginManager().registerEvents(new leech(), this);
        getServer().getPluginManager().registerEvents(new scope(), this);


        /*
        getServer().getPluginManager().registerEvents(new currency(), this);
        */

        getServer().getPluginManager().registerEvents(new RollItem(), this);
        getCommand("showgui").setExecutor(new RollItem());


        //item name index for command completions (idek how to handle this better)
        allItems = getAllItems();

        allItemsAsString = getAllItemsAsString();

        registerCommands();

        addAllEffects();

        //loaded message
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "+++ RebelItems");
    }

    public static Set<Effect> allEffects = new HashSet<>();

    private void addAllEffects(){
        allEffects.add(new ice());
        allEffects.add(new midas());
        allEffects.add(new leech());
        allEffects.add(new scope());
    }

    public static Set<Effect> getAllEffects(){
        return allEffects;
    }

    //item name index for command completions (idek how to handle this better)
    public List<Item> getAllItems() {
        List<Item> allItems = Arrays.asList(
/*                new foundersSword(),
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
                new boostElytra(),
                new treefellerAxe(),
                new token(),*/
                new midasSword()
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

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

    public static void LogToConsole(String log){
        Bukkit.getLogger().log(Level.INFO,log);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "--- RebelItems");
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));


    }

}
