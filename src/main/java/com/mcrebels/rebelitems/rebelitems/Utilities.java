package com.mcrebels.rebelitems.rebelitems;

import org.bukkit.entity.Player;


public class Utilities {

    public static boolean metaCheck(Player player, Integer metaID){
        if (player.getInventory().getItemInMainHand().hasItemMeta()){
            if (player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()){
                return player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == metaID;
            }
        }
        return false;
    }
}
