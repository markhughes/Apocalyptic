/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cyberninjapiggy.apocalyptic.events;

import net.cyberninjapiggy.apocalyptic.Apocalyptic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Nick
 */
public class PlayerDamaged implements Listener {
    private final Apocalyptic a;
    @EventHandler
    public void onPlayerDamagedByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            if (a.worldEnabledZombie(e.getEntity().getWorld().getName()) && e.getDamager().getType() == EntityType.ZOMBIE) {
                e.setDamage(e.getDamage() * a.getConfig().getWorld(e.getEntity().getWorld()).getInt("mobs.zombies.damageMultiplier"));
                if (a.getConfig().getWorld(e.getEntity().getWorld()).getBoolean("mobs.zombies.effects.hunger")) {
                    ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 1, 20));
                }
                if (a.getConfig().getWorld(e.getEntity().getWorld()).getBoolean("mobs.zombies.effects.weakness")) {
                    ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 1, 20));
                }
                if (a.getConfig().getWorld(e.getEntity().getWorld()).getBoolean("mobs.zombies.effects.slowness")) {
                    ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1, 20));
                }
                if (a.getConfig().getWorld(e.getEntity().getWorld()).getBoolean("mobs.zombies.effects.nausea")) {
                    ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 1, 20));
                }
            }
        }
    }
    @EventHandler
    public void onPlayerDamaged(EntityDamageEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            if (a.worldEnabledFallout(e.getEntity().getWorld().getName())) {
                
                if (a.getPlayerRadiation((Player) e.getEntity()) >= 10) {
                    e.setDamage(e.getDamage() * (int) 4);
                }
                else if (a.getPlayerRadiation((Player) e.getEntity()) >= 1) {
                    e.setDamage(e.getDamage() * (int) 2);
                }
            }
        }
    }
    public PlayerDamaged(Apocalyptic a) {
        this.a = a;
    } 
}
