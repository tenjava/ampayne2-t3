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
import me.ampayne2.randomevents.api.WorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.plugin.PluginManager;

public class CreeperExplodeEvent extends WorldEvent {
    public CreeperExplodeEvent() {
        super("CreeperExplode");
        setProbability(1);
        setDescription("Makes all the creepers in the world explode.");
        setOccurMessage("Creepers everywhere have mysteriously exploded!");
    }

    @Override
    public void trigger(RandomEvents plugin, World world) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        for (LivingEntity entity : world.getLivingEntities()) {
            if (entity.getType() == EntityType.CREEPER) {
                Creeper creeper = (Creeper) entity;

                float power = creeper.isPowered() ? 6.0F : 3.0F;
                ExplosionPrimeEvent event = new ExplosionPrimeEvent(creeper, power, false);
                pluginManager.callEvent(event);
                if (!event.isCancelled()) {
                    creeper.damage(creeper.getHealth());
                    world.createExplosion(creeper.getLocation(), power);
                }
            }
        }
        plugin.getMessenger().broadcastEventMessage(this);
    }
}
