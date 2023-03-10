package com.mcrebels.rebelitems.rebelitems;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Set;
import java.util.function.IntToDoubleFunction;


public class Utilities {

    public static boolean metaCheck(Player player, Integer metaID){
        if (player.getInventory().getItemInMainHand().hasItemMeta()){
            if (player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()){
                return player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == metaID;
            }
        }
        return false;
    }

    public static Pair<String, Double> generateRandom(double min, double max) {
        String color = "";
        int percential = (int) (Math.random() * 101);
        double range = max - min;
        double value = (double)percential * range + min * 100;
        BigDecimal bd = new BigDecimal(value);
        bd = bd.round(new MathContext(3));
        value = bd.doubleValue();
        if (percential == 100){
            color = "<#ffc400>";
        }
        else if (percential == 0){
            color = "<#521717>";
        }
        else if (percential >= 90){
            color = "<#15ff019>";
        }
        else if (percential >= 75){
            color = "<#4fc74c>";
        }
        else if (percential >= 60){
            color = "<#5a9f5a>";
        }
        else if (percential > 50){
            color = "<#577a57>";
        }
        else if (percential == 50){
            color = "<#555555>";
        }
        else if (percential < 50){
            color = "<#875d5d>";
        }
        else if (percential < 40){
            color = "<#b96565>";
        }
        else if (percential < 25){
            color = "<#df3e3e>";
        }
        else if (percential < 10){
            color = "<#ff0101>";
        }
        color += value;
        value /= 100;

        Pair<String, Double> pair = new Pair<String, Double>(color, value);
        return pair;
    }

        public static Pair<String, Integer> generateRandomInt(Integer min, Integer max) {
        String color = "";
        Integer value = min + (int)(Math.random() * ((max - min) + 1));
        Double percentile = (((double)(value - min) / (double)(max - min)) * 100);
        if (percentile == 100){
            color = "<#ffc400>";
        }
        else if (percentile == 0){
            color = "<#521717>";
        }
        else if (percentile >= 90){
            color = "<#15ff019>";
        }
        else if (percentile >= 75){
            color = "<#4fc74c>";
        }
        else if (percentile >= 60){
            color = "<#5a9f5a>";
        }
        else if (percentile > 50){
            color = "<#577a57>";
        }
        else if (percentile == 50){
            color = "<#555555>";
        }
        else if (percentile < 50){
            color = "<#875d5d>";
        }
        else if (percentile < 40){
            color = "<#b96565>";
        }
        else if (percentile < 25){
            color = "<#df3e3e>";
        }
        else if (percentile < 10){
            color = "<#ff0101>";
        }
        color += value;

        Pair<String, Integer> pair = new Pair<String, Integer>(color, value);
        return pair;
    }

    public static Double generateRandomDouble(int min, int max){
        Double mi = (min/100.0);
        Double ma = (max/100.0);
        Random rnd = new Random();
        Double val =  rnd.nextDouble(mi, ma);
        return val;
    }

    //This is a very simple precision function to easily change the global precision (used in the legacy fix)
    public static int getPrecision() {
        return 3;
    }

    //Used to combine PDC's for Item effects
    public static PersistentDataContainer mergePDC(PersistentDataContainer one,PersistentDataContainer two ){
        PersistentDataContainer container = one.getAdapterContext().newPersistentDataContainer();
        Set<NamespacedKey>  a = one.getKeys();
        a.addAll(two.getKeys());
        for (NamespacedKey k :
             a) {
            if (one.getKeys().contains(k)){
                container.set(k, PersistentDataType.DOUBLE, one.get(k, PersistentDataType.DOUBLE));
            }
            if (two.getKeys().contains(k)){
                container.set(k, PersistentDataType.DOUBLE, two.get(k, PersistentDataType.DOUBLE));
            }

        }
        return container;
    }

    public static PersistentDataContainer mergePDCSet(Set<PersistentDataContainer> s){
        PersistentDataContainer container = null;
        for (PersistentDataContainer p :
                s) {
            if (container == null){
                container = p.getAdapterContext().newPersistentDataContainer();
            }
            for (NamespacedKey k :
                    p.getKeys()) {
                container.set(k, PersistentDataType.DOUBLE,p.get(k,PersistentDataType.DOUBLE));
            }
        }
        return container;
    }
}
