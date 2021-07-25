package com.mcrebels.rebelitems.rebelitems;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

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

    public static String getWorthColour(double min, double max, double num){
        DecimalFormat fmt = new DecimalFormat("#.#");
        fmt.setRoundingMode(RoundingMode.HALF_UP);
        double percent = ((num-min)/(max-min))*100;
        num = Double.parseDouble(fmt.format(num*100));
        //#ff0101 #df3e3e #b96565 #875d5d //#555555// #577a57 #5a9f5a #4fc74c #15ff019

        String toReturn = ""+num;
        if (percent == 100){
            toReturn = "<#ffc400>"+num;
        }
        else if (percent == 0){
            toReturn = "<#521717>"+num;
        }
        else if (percent >= 90){
            toReturn = "<#15ff019>"+num;
        }
        else if (percent >= 75){
            toReturn = "<#4fc74c>"+num;
        }
        else if (percent >= 60){
            toReturn = "<#5a9f5a>"+num;
        }
        else if (percent > 50){
            toReturn = "<#577a57>"+num;
        }
        else if (percent == 50){
            toReturn = "<#555555>"+num;
        }
        else if (percent < 50){
            toReturn = "<#875d5d>"+num;
        }
        else if (percent < 40){
            toReturn = "<#b96565>"+num;
        }
        else if (percent < 25){
            toReturn = "<#df3e3e>"+num;
        }
        else if (percent < 10){
            toReturn = "<#ff0101>"+num;
        }
        return toReturn;
    }

}
