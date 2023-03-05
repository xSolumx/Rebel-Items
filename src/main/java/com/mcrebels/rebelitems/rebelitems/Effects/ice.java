package com.mcrebels.rebelitems.rebelitems.Effects;

import com.mcrebels.rebelitems.rebelitems.Pair;
import com.mcrebels.rebelitems.rebelitems.RebelItems;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Random;
import java.util.logging.Level;

public class ice implements Effect {
    private static final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    Economy eco = RebelItems.getEconomy();

    //Standard limits for this effect
    private static double maxVal = 3.0;
    private static double minVal = 1.0;
    private static double modifier;


    @EventHandler
    public  void onSwordHit(EntityDamageByEntityEvent e){
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
                    for (NamespacedKey k:
                            sub.getKeys()) {
                        if (k.equals(iceModifier)){
                            Double mod = sub.get(k, PersistentDataType.DOUBLE);
                            int a = (int) (Math.random()*maxVal*10);
                            a = e.getEntity().getMaxFreezeTicks()/20*a;
                            e.getEntity().setFreezeTicks(a);
                            Bukkit.getLogger().log(Level.INFO,"Set freeze ticks to: " + a);
                        }

                        //Bukkit.getLogger().log(Level.INFO, "KEY: " + k.getKey());
                    }
                }
                else {
                    Bukkit.getLogger().log(Level.INFO, "Something went wrong");
                }

            }

        }
    }
    private static final NamespacedKey iceMax = new NamespacedKey(plugin, "iceMax");
    private static final NamespacedKey iceMin = new NamespacedKey(plugin, "iceMin");
    private static final NamespacedKey iceModifier = new NamespacedKey(plugin, "iceModifier");

    public static PersistentDataContainer getPDC(ItemStack item){
        PersistentDataContainer pd = item.getItemMeta().getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
        pd.set(iceMax, PersistentDataType.DOUBLE,maxVal);
        pd.set(iceMin, PersistentDataType.DOUBLE,minVal);
        pd.set(iceModifier,PersistentDataType.DOUBLE,0.5);
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
