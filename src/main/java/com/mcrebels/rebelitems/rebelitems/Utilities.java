package com.mcrebels.rebelitems.rebelitems;

import com.sun.tools.javac.util.Pair;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;


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
        double value = (double)percential * range + min;
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
        color += value + "%";
        value /= 100;

        Pair<String, Double> pair = new Pair<String, Double>(color, value);
        return pair;
    }
}
