package com.mcrebels.rebelitems.rebelitems.allItems;

import com.mcrebels.rebelitems.rebelitems.RebelItems;
import com.mcrebels.rebelitems.rebelitems.allItems.misc.chickenBones;
import com.sk89q.worldedit.world.registry.BlockMaterial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.Bukkit.getServer;
import static org.bukkit.Material.getMaterial;
import static org.bukkit.Material.matchMaterial;

public class randomPickaxe extends Item implements Listener {
    private ItemStack item;

    private final Integer customMetaID = 10;
    private final Component itemName = MiniMessage.markdown().parse("<gradient:#5e4fa2:#f79459>Gambler's Pickaxe</gradient>");
    private final Material itemMaterial = Material.NETHERITE_PICKAXE;
    private final List<String> listOfLines;
    private final List<Component> itemLore = Arrays.asList(
            MiniMessage.markdown().parse("<gradient:yellow:blue>===================</gradient>"),
            MiniMessage.markdown().parse("<gradient:yellow:blue>From the treasury of the Chicken Lord himself</gradient>"),
            MiniMessage.markdown().parse("Occasionally gives the user a random minecraft block in"),
            MiniMessage.markdown().parse("addition to the broken block."));



    public randomPickaxe(){
        item = new ItemStack(itemMaterial, 1);
        listOfLines = getMaterialList();
        ItemMeta tMeta = item.getItemMeta();
        tMeta.setCustomModelData(customMetaID);
        tMeta.lore(itemLore);
        tMeta.displayName(itemName);
        item.setItemMeta(tMeta);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent b){
        if(b.getPlayer() != null &&
                b.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() &&
                b.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 8) {
            if(Math.random() < .005) {
                Player player = b.getPlayer();
                Location dropsLoc = b.getBlock().getLocation();

                Material randomMaterial;
                randomMaterial = matchMaterial(listOfLines.get((int)(Math.random() * listOfLines.size())));
                player.getWorld().dropItemNaturally(dropsLoc, new ItemStack(randomMaterial, 1));
            }
        }
    }

    @Override
    public String getName() {
        return "GamblerPickaxe";
    }

    public ItemStack getItem(){
        return item;
    }

    private List<String> getMaterialList() {
        List<String> materials = Arrays.asList("stone", "granite", "polished_granite", "diorite", "polished_diorite", "andesite", "polished_andesite", "grass_block", "dirt", "coarse_dirt", "podzol", "crimson_nylium", "warped_nylium", "cobblestone", "oak_planks", "spruce_planks", "birch_planks", "jungle_planks", "acacia_planks", "dark_oak_planks", "crimson_planks", "warped_planks", "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling", "sand", "red_sand", "gravel", "gold_ore", "iron_ore", "coal_ore", "nether_gold_ore", "oak_log", "spruce_log", "birch_log", "jungle_log", "acacia_log", "dark_oak_log", "crimson_stem", "warped_stem", "stripped_oak_log", "stripped_spruce_log", "stripped_birch_log", "stripped_jungle_log", "stripped_acacia_log", "stripped_dark_oak_log", "stripped_crimson_stem", "stripped_warped_stem", "stripped_oak_wood", "stripped_spruce_wood", "stripped_birch_wood", "stripped_jungle_wood", "stripped_acacia_wood", "stripped_dark_oak_wood", "stripped_crimson_hyphae", "stripped_warped_hyphae", "oak_wood", "spruce_wood", "birch_wood", "jungle_wood", "acacia_wood", "dark_oak_wood", "crimson_hyphae", "warped_hyphae", "oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves", "acacia_leaves", "dark_oak_leaves", "sponge", "wet_sponge", "glass", "lapis_ore", "lapis_block", "dispenser", "sandstone", "chiseled_sandstone", "cut_sandstone", "note_block", "powered_rail", "detector_rail", "sticky_piston", "cobweb", "grass", "fern", "dead_bush", "seagrass", "sea_pickle", "piston", "white_wool", "orange_wool", "magenta_wool", "light_blue_wool", "yellow_wool", "lime_wool", "pink_wool", "gray_wool", "light_gray_wool", "cyan_wool", "purple_wool", "blue_wool", "brown_wool", "green_wool", "red_wool", "black_wool", "dandelion", "poppy", "blue_orchid", "allium", "azure_bluet", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy", "cornflower", "lily_of_the_valley", "wither_rose", "brown_mushroom", "red_mushroom", "crimson_fungus", "warped_fungus", "crimson_roots", "warped_roots", "nether_sprouts", "weeping_vines", "twisting_vines", "sugar_cane", "kelp", "bamboo", "gold_block", "iron_block", "oak_slab", "spruce_slab", "birch_slab", "jungle_slab", "acacia_slab", "dark_oak_slab", "crimson_slab", "warped_slab", "stone_slab", "smooth_stone_slab", "sandstone_slab", "cut_sandstone_slab", "petrified_oak_slab", "cobblestone_slab", "brick_slab", "stone_brick_slab", "nether_brick_slab", "quartz_slab", "red_sandstone_slab", "cut_red_sandstone_slab", "purpur_slab", "prismarine_slab", "prismarine_brick_slab", "dark_prismarine_slab", "smooth_quartz", "smooth_red_sandstone", "smooth_sandstone", "smooth_stone", "bricks", "tnt", "bookshelf", "mossy_cobblestone", "obsidian", "torch", "end_rod", "chorus_plant", "chorus_flower", "purpur_block", "purpur_pillar", "purpur_stairs", "oak_stairs", "chest", "diamond_ore", "diamond_block", "crafting_table", "farmland", "furnace", "ladder", "rail", "cobblestone_stairs", "lever", "stone_pressure_plate", "oak_pressure_plate", "spruce_pressure_plate", "birch_pressure_plate", "jungle_pressure_plate", "acacia_pressure_plate", "dark_oak_pressure_plate", "crimson_pressure_plate", "warped_pressure_plate", "polished_blackstone_pressure_plate", "redstone_ore", "redstone_torch", "snow", "ice", "snow_block", "cactus", "clay", "jukebox", "oak_fence", "spruce_fence", "birch_fence", "jungle_fence", "acacia_fence", "dark_oak_fence", "crimson_fence", "warped_fence", "pumpkin", "carved_pumpkin", "netherrack", "soul_sand", "soul_soil", "basalt", "polished_basalt", "soul_torch", "glowstone", "jack_o_lantern", "oak_trapdoor", "spruce_trapdoor", "birch_trapdoor", "jungle_trapdoor", "acacia_trapdoor", "dark_oak_trapdoor", "crimson_trapdoor", "warped_trapdoor", "infested_stone", "infested_cobblestone", "infested_stone_bricks", "infested_mossy_stone_bricks", "infested_cracked_stone_bricks", "infested_chiseled_stone_bricks", "stone_bricks", "mossy_stone_bricks", "cracked_stone_bricks", "chiseled_stone_bricks", "brown_mushroom_block", "red_mushroom_block", "mushroom_stem", "iron_bars", "chain", "glass_pane", "melon", "vine", "oak_fence_gate", "spruce_fence_gate", "birch_fence_gate", "jungle_fence_gate", "acacia_fence_gate", "dark_oak_fence_gate", "crimson_fence_gate", "warped_fence_gate", "brick_stairs", "stone_brick_stairs", "mycelium", "lily_pad", "nether_bricks", "cracked_nether_bricks", "chiseled_nether_bricks", "nether_brick_fence", "nether_brick_stairs", "enchanting_table", "end_stone", "end_stone_bricks", "redstone_lamp", "sandstone_stairs", "emerald_ore", "ender_chest", "tripwire_hook", "emerald_block", "spruce_stairs", "birch_stairs", "jungle_stairs", "crimson_stairs", "warped_stairs", "beacon", "cobblestone_wall", "mossy_cobblestone_wall", "brick_wall", "prismarine_wall", "red_sandstone_wall", "mossy_stone_brick_wall", "granite_wall", "stone_brick_wall", "nether_brick_wall", "andesite_wall", "red_nether_brick_wall", "sandstone_wall", "end_stone_brick_wall", "diorite_wall", "blackstone_wall", "polished_blackstone_wall", "polished_blackstone_brick_wall", "stone_button", "oak_button", "spruce_button", "birch_button", "jungle_button", "acacia_button", "dark_oak_button", "crimson_button", "warped_button", "polished_blackstone_button", "anvil", "chipped_anvil", "damaged_anvil", "trapped_chest", "light_weighted_pressure_plate", "heavy_weighted_pressure_plate", "daylight_detector", "redstone_block", "nether_quartz_ore", "hopper", "chiseled_quartz_block", "quartz_block", "quartz_bricks", "quartz_pillar", "quartz_stairs", "activator_rail", "dropper", "white_terracotta", "orange_terracotta", "magenta_terracotta", "light_blue_terracotta", "yellow_terracotta", "lime_terracotta", "pink_terracotta", "gray_terracotta", "light_gray_terracotta", "cyan_terracotta", "purple_terracotta", "blue_terracotta", "brown_terracotta", "green_terracotta", "red_terracotta", "black_terracotta", "iron_trapdoor", "hay_block", "white_carpet", "orange_carpet", "magenta_carpet", "light_blue_carpet", "yellow_carpet", "lime_carpet", "pink_carpet", "gray_carpet", "light_gray_carpet", "cyan_carpet", "purple_carpet", "blue_carpet", "brown_carpet", "green_carpet", "red_carpet", "black_carpet", "terracotta", "coal_block", "packed_ice", "acacia_stairs", "dark_oak_stairs", "slime_block", "grass_path", "sunflower", "lilac", "rose_bush", "peony", "tall_grass", "large_fern", "white_stained_glass", "orange_stained_glass", "magenta_stained_glass", "light_blue_stained_glass", "yellow_stained_glass", "lime_stained_glass", "pink_stained_glass", "gray_stained_glass", "light_gray_stained_glass", "cyan_stained_glass", "purple_stained_glass", "blue_stained_glass", "brown_stained_glass", "green_stained_glass", "red_stained_glass", "black_stained_glass", "white_stained_glass_pane", "orange_stained_glass_pane", "magenta_stained_glass_pane", "light_blue_stained_glass_pane", "yellow_stained_glass_pane", "lime_stained_glass_pane", "pink_stained_glass_pane", "gray_stained_glass_pane", "light_gray_stained_glass_pane", "cyan_stained_glass_pane", "purple_stained_glass_pane", "blue_stained_glass_pane", "brown_stained_glass_pane", "green_stained_glass_pane", "red_stained_glass_pane", "black_stained_glass_pane", "prismarine", "prismarine_bricks", "dark_prismarine", "prismarine_stairs", "prismarine_brick_stairs", "dark_prismarine_stairs", "sea_lantern", "red_sandstone", "chiseled_red_sandstone", "cut_red_sandstone", "red_sandstone_stairs", "magma_block", "nether_wart_block", "warped_wart_block", "red_nether_bricks", "bone_block", "observer", "shulker_box", "white_shulker_box", "orange_shulker_box", "magenta_shulker_box", "light_blue_shulker_box", "yellow_shulker_box", "lime_shulker_box", "pink_shulker_box", "gray_shulker_box", "light_gray_shulker_box", "cyan_shulker_box", "purple_shulker_box", "blue_shulker_box", "brown_shulker_box", "green_shulker_box", "red_shulker_box", "black_shulker_box", "white_glazed_terracotta", "orange_glazed_terracotta", "magenta_glazed_terracotta", "light_blue_glazed_terracotta", "yellow_glazed_terracotta", "lime_glazed_terracotta", "pink_glazed_terracotta", "gray_glazed_terracotta", "light_gray_glazed_terracotta", "cyan_glazed_terracotta", "purple_glazed_terracotta", "blue_glazed_terracotta", "brown_glazed_terracotta", "green_glazed_terracotta", "red_glazed_terracotta", "black_glazed_terracotta", "white_concrete", "orange_concrete", "magenta_concrete", "light_blue_concrete", "yellow_concrete", "lime_concrete", "pink_concrete", "gray_concrete", "light_gray_concrete", "cyan_concrete", "purple_concrete", "blue_concrete", "brown_concrete", "green_concrete", "red_concrete", "black_concrete", "white_concrete_powder", "orange_concrete_powder", "magenta_concrete_powder", "light_blue_concrete_powder", "yellow_concrete_powder", "lime_concrete_powder", "pink_concrete_powder", "gray_concrete_powder", "light_gray_concrete_powder", "cyan_concrete_powder", "purple_concrete_powder", "blue_concrete_powder", "brown_concrete_powder", "green_concrete_powder", "red_concrete_powder", "black_concrete_powder", "turtle_egg", "dead_tube_coral_block", "dead_brain_coral_block", "dead_bubble_coral_block", "dead_fire_coral_block", "dead_horn_coral_block", "tube_coral_block", "brain_coral_block", "bubble_coral_block", "fire_coral_block", "horn_coral_block", "tube_coral", "brain_coral", "bubble_coral", "fire_coral", "horn_coral", "dead_brain_coral", "dead_bubble_coral", "dead_fire_coral", "dead_horn_coral", "dead_tube_coral", "tube_coral_fan", "brain_coral_fan", "bubble_coral_fan", "fire_coral_fan", "horn_coral_fan", "dead_tube_coral_fan", "dead_brain_coral_fan", "dead_bubble_coral_fan", "dead_fire_coral_fan", "dead_horn_coral_fan", "blue_ice", "conduit", "polished_granite_stairs", "smooth_red_sandstone_stairs", "mossy_stone_brick_stairs", "polished_diorite_stairs", "mossy_cobblestone_stairs", "end_stone_brick_stairs", "stone_stairs", "smooth_sandstone_stairs", "smooth_quartz_stairs", "granite_stairs", "andesite_stairs", "red_nether_brick_stairs", "polished_andesite_stairs", "diorite_stairs", "polished_granite_slab", "smooth_red_sandstone_slab", "mossy_stone_brick_slab", "polished_diorite_slab", "mossy_cobblestone_slab", "end_stone_brick_slab", "smooth_sandstone_slab", "smooth_quartz_slab", "granite_slab", "andesite_slab", "red_nether_brick_slab", "polished_andesite_slab", "diorite_slab", "scaffolding", "iron_door", "oak_door", "spruce_door", "birch_door", "jungle_door", "acacia_door", "dark_oak_door", "crimson_door", "warped_door", "repeater", "comparator", "wheat", "oak_sign", "spruce_sign", "birch_sign", "jungle_sign", "acacia_sign", "dark_oak_sign", "crimson_sign", "warped_sign", "dried_kelp_block", "cake", "white_bed", "orange_bed", "magenta_bed", "light_blue_bed", "yellow_bed", "lime_bed", "pink_bed", "gray_bed", "light_gray_bed", "cyan_bed", "purple_bed", "blue_bed", "brown_bed", "green_bed", "red_bed", "black_bed", "nether_wart", "brewing_stand", "cauldron", "flower_pot", "skeleton_skull", "wither_skeleton_skull", "zombie_head", "creeper_head", "dragon_head", "loom", "composter", "barrel", "smoker", "blast_furnace", "cartography_table", "fletching_table", "grindstone", "lectern", "smithing_table", "stonecutter", "bell", "lantern", "soul_lantern", "campfire", "soul_campfire", "shroomlight", "bee_nest", "beehive", "honey_block", "honeycomb_block", "lodestone", "netherite_block", "ancient_debris", "target", "crying_obsidian", "blackstone", "blackstone_slab", "blackstone_stairs", "gilded_blackstone", "polished_blackstone", "polished_blackstone_slab", "polished_blackstone_stairs", "chiseled_polished_blackstone", "polished_blackstone_bricks", "polished_blackstone_brick_slab", "polished_blackstone_brick_stairs", "cracked_polished_blackstone_bricks", "respawn_anchor", "torch", "oak_sign", "spruce_sign", "birch_sign", "acacia_sign", "jungle_sign", "dark_oak_sign", "redstone_torch", "soul_torch", "crimson_sign", "warped_sign");

        return materials;
    }
}
