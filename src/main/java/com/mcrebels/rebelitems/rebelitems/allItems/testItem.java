package com.mcrebels.rebelitems.rebelitems.allItems;

import com.mcrebels.rebelitems.rebelitems.Effects.ice;
import com.mcrebels.rebelitems.rebelitems.Effects.leech;
import com.mcrebels.rebelitems.rebelitems.Effects.midas;
import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.Utilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class testItem extends Item implements Listener {
    private final Plugin plugin = RebelItems.getPlugin(RebelItems.class);
    private ItemStack s = new ItemStack(Material.NETHERITE_SWORD,1);

    //Effect Keys stored in Persistent Data Containers
    //Get from Effects package
    private PersistentDataContainer midasPDC = midas.getPDC(s);
    private PersistentDataContainer icePDC = ice.getPDC(s);
    private PersistentDataContainer leechPDC = leech.getPDC(s);

    //Using a Set
    private Set<PersistentDataContainer> PDCset = new HashSet<>();

    public testItem(){
        PDCset.add(midasPDC);
        PDCset.add(icePDC);
        PDCset.add(leechPDC);

        //Add all effects to a single PDC to reduce code repetition when looking for effects in Listeners
        PersistentDataContainer PDCToUse = Utilities.mergePDCSet(PDCset);
        //effects super key
        NamespacedKey Effects = new NamespacedKey(plugin,"effects");

        //Adds PDC brought from Effects package
        ItemMeta m = s.getItemMeta();
        m.getPersistentDataContainer().set(Effects, PersistentDataType.TAG_CONTAINER,PDCToUse);
        s.setItemMeta(m);
    }



    public void setMinMax(Double min, Double max){
        updateLore();
    }

    public void updateLore(){
        ItemMeta meta = this.getItem().getItemMeta();
        List<Component> loreList = meta.lore();
        for (Component c :
                loreList) {
            if (c.toString().contains("Min")){

                //update min lore value
            }
            if (c.toString().contains("max")){

                //update max lore value
            }
        }
        this.getItem().setItemMeta(meta);
    }

    @Override
    public String getName() {
        return "Test Sword";
    }

    @Override
    public void checkBounds(Player player) {

    }
    @Override
    public ItemStack getItem(){
        return s;
    }
}
