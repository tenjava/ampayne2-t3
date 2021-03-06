/*
 * This file is part of RandomEvents.
 *
 * Copyright (c) 2014 <http://dev.bukkit.org/server-mods/randomevents//>
 *
 * RandomEvents is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RandomEvents is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RandomEvents.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ampayne2.randomevents.events;

import me.ampayne2.randomevents.RandomEvents;
import me.ampayne2.randomevents.api.PlayerEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OreSmeltEvent extends PlayerEvent {
    public OreSmeltEvent() {
        super("OreSmelt");
        setProbability(7);
        setDescription("Smelts all the ore in a random player's inventory.");
        setOccurMessage("You got lucky and your ore was smelted!");
    }

    @Override
    @SuppressWarnings("deprecation")
    public void trigger(RandomEvents plugin, Player player) {
        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack != null && isSmeltableOre(itemStack.getType())) {
                smelt(itemStack);
            }
        }
        player.updateInventory();
        plugin.getMessenger().sendEventMessage(player, this);
    }

    /**
     * Checks if a Material is a smeltable ore.
     *
     * @param material The Material.
     * @return True if the Material is a smeltable ore, else false.
     */
    private boolean isSmeltableOre(Material material) {
        switch (material) {
            case COAL_ORE:
            case IRON_ORE:
            case GOLD_ORE:
            case QUARTZ_ORE:
                return true;
            default:
                return false;
        }
    }

    /**
     * Smelts an ItemStack.
     *
     * @param itemStack The ItemStack to smelt.
     */
    private void smelt(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case COAL_ORE:
                itemStack.setType(Material.COAL);
                break;
            case IRON_ORE:
                itemStack.setType(Material.IRON_INGOT);
                break;
            case GOLD_ORE:
                itemStack.setType(Material.GOLD_INGOT);
                break;
            case QUARTZ_ORE:
                itemStack.setType(Material.QUARTZ);
                break;
        }
    }
}
