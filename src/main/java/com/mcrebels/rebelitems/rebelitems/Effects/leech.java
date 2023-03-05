package com.mcrebels.rebelitems.rebelitems.Effects;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Random;
import java.util.logging.Level;

public class leech implements Effect {

    private static final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    Economy eco = RebelItems.getEconomy();

    //Standard limits for this effect
    private static double maxVal = 0.1;
    private static double minVal = -0.05;
    private static double val = 0;


    @EventHandler
    public  void onWeaponHit(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
            ItemStack item = player.getInventory().getItemInMainHand();
            //Debugging
            //Bukkit.getLogger().log(Level.INFO,"keys: " + item.getItemMeta().getPersistentDataContainer().getKeys());
            PersistentDataContainer root = item.getItemMeta().getPersistentDataContainer();

            for (NamespacedKey n:
                    root.getKeys()) {
                if (n.getKey().contains("effects")){
                    PersistentDataContainer sub = root.get(n, PersistentDataType.TAG_CONTAINER);
                    if (sub.getKeys().contains(leechModifier)) {
                        double damageDone = e.getDamage();
                        double leechAmount;
                        for (NamespacedKey k :
                                sub.getKeys()) {
                            if (k.equals(leechModifier)) {
                                Double mod = sub.get(k, PersistentDataType.DOUBLE);
                                leechAmount = mod*damageDone;
                                if (player.getHealth()+leechAmount >= 20){
                                    player.setHealth(20);
                                    Bukkit.getLogger().log(Level.INFO, "Leeched " + leechAmount + " health from" + e.getEntity().getName());
                                }
                                else {
                                    player.setHealth(player.getHealth()+leechAmount);
                                    Bukkit.getLogger().log(Level.INFO, "Leeched " + leechAmount + " health from" + e.getEntity().getName());
                                }
                            }
                            //Bukkit.getLogger().log(Level.INFO, "KEY: " + k.getKey());
                        }
                    }
                }
                else {
                    Bukkit.getLogger().log(Level.INFO, "Something went wrong");
                }

            }
        }
    }

    private static final NamespacedKey leechMax = new NamespacedKey(plugin, "leechMax");
    private static final NamespacedKey leechMin = new NamespacedKey(plugin, "leechMin");
    private static final NamespacedKey leechModifier = new NamespacedKey(plugin, "leechVal");

    public static PersistentDataContainer getPDC(ItemStack item){
        PersistentDataContainer pd = item.getItemMeta().getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
        pd.set(leechMax, PersistentDataType.DOUBLE,maxVal);
        pd.set(leechMin, PersistentDataType.DOUBLE,minVal);
        pd.set(leechModifier,PersistentDataType.DOUBLE,val);
        x=pd;
        return pd;
    }

    static PersistentDataContainer x;
    @Override
    public PersistentDataContainer getP(){
        return x;
    }


    public leech(){
        Random rnd = new Random();
    }

    public static Double getMin(){
        return minVal;
    }

    public static Double getMax(){
        return maxVal;
    }
}
