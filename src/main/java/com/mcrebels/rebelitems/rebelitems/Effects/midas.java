package com.mcrebels.rebelitems.rebelitems.Effects;

import com.mcrebels.rebelitems.rebelitems.Pair;
import com.mcrebels.rebelitems.rebelitems.RebelItems;
import net.kyori.adventure.key.Key;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
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

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

public class midas implements Effect {
    private static final Plugin plugin = RebelItems.getPlugin(RebelItems.class);

    private static final Double maxVal = 20.0;
    private static final Double minVal = 1.0;
    private Economy eco = RebelItems.getEconomy();


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
                    PersistentDataContainer sub = root.get(n,PersistentDataType.TAG_CONTAINER);
                    for (NamespacedKey k:
                         sub.getKeys()) {
                        if (k.equals(midasModifier)){
                            Double mod = sub.get(k, PersistentDataType.DOUBLE);
                            Double a = Math.random()*maxVal;
                            eco.depositPlayer(player,a);
                        }
                    }
                }
                else {
                    Bukkit.getLogger().log(Level.INFO, "Something went wrong");
                }

            }

        }
    }


    private static final NamespacedKey midasMax = new NamespacedKey(plugin, "midasMax");
    private static final NamespacedKey midasMin = new NamespacedKey(plugin, "midasMin");
    private static final NamespacedKey midasModifier = new NamespacedKey(plugin, "midasModifier");

    public static PersistentDataContainer getPDC(ItemStack item){
        PersistentDataContainer pd = item.getItemMeta().getPersistentDataContainer().getAdapterContext().newPersistentDataContainer();
        pd.set(midasMax, PersistentDataType.DOUBLE,maxVal);
        pd.set(midasMin, PersistentDataType.DOUBLE,minVal);
        pd.set(midasModifier,PersistentDataType.DOUBLE,0.5);
        x=pd;
        return pd;
    }

    static PersistentDataContainer x;
    @Override
    public PersistentDataContainer getP(){
        return x;
    }


    public midas(){
        Random rnd = new Random();
    }

    public static Pair<String, Double> getMin(){
        return new Pair<>("min", minVal);
    }

    public static Pair<String,Double> getMax(){
        return new Pair<>("max", maxVal);
    }
}
