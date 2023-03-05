package com.mcrebels.rebelitems.rebelitems.Effects;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

public class scope implements Effect {
    private static final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    Economy eco = RebelItems.getEconomy();

    //Standard limits for this effect
    private static double DMG = 2.0;
    private static double modifier;


    Set<Player> playersHolding = new HashSet<>();
    @EventHandler
    public  void onHoldRightC(PlayerInteractEvent e){
        if (e.getAction().isRightClick()){
            if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
                Player player = (Player) e.getPlayer();
                player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
                ItemStack item = player.getInventory().getItemInMainHand();
                //Debugging
                //Bukkit.getLogger().log(Level.INFO,"keys: " + item.getItemMeta().getPersistentDataContainer().getKeys());
                PersistentDataContainer root = item.getItemMeta().getPersistentDataContainer();

                for (NamespacedKey n :
                        root.getKeys()) {
                    if (n.getKey().contains("effects")) {
                        PersistentDataContainer sub = root.get(n, PersistentDataType.TAG_CONTAINER);
                        for (NamespacedKey k : sub.getKeys()) {
                            if (k.equals(scope)) {
                                Double damage = sub.get(scopeDMG, PersistentDataType.DOUBLE);
                                Double rMaxMultipl = sub.get(scopeRandomMaxMultiplier, PersistentDataType.DOUBLE);
                                
                                for (NamespacedKey sK: sub.getKeys()){
                                    if (k.equals(sK)){

                                    }
                                }
 ;
                            }

                            //Bukkit.getLogger().log(Level.INFO, "KEY: " + k.getKey());
                        }
                    } else {
                        Bukkit.getLogger().log(Level.INFO, "Something went wrong");
                    }

                }
            }

        }
    }
    private static final NamespacedKey scopeProbAffinity = new NamespacedKey(plugin, "scopeProbAffinity");
    private static final NamespacedKey scopeRandomMaxMultiplier = new NamespacedKey(plugin, "scopeRandomMaxMultiplier");
    private static final NamespacedKey scopeDMG = new NamespacedKey(plugin, "scopeDMG");
    private static final NamespacedKey scope = new NamespacedKey(plugin, "scope");

    public static PersistentDataContainer getPDC(ItemStack item){
        PersistentDataContainer pd = item.getItemMeta().getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
        pd.set(scope, PersistentDataType.STRING, "present");
        pd.set(scopeDMG, PersistentDataType.DOUBLE,DMG);
        pd.set(scopeRandomMaxMultiplier,PersistentDataType.DOUBLE,0.5);
        pd.set(scopeProbAffinity, PersistentDataType.DOUBLE,0.90);
        x=pd;
        return pd;
    }
    static PersistentDataContainer x;

    @Override
    public PersistentDataContainer getP(){
        return x;
    }


    public ice(){
        Random rnd = new Random();
    }

    public static Double getMin(){
        return minVal;
    }

    public static Double getMax(){
        return maxVal;
    }


}
