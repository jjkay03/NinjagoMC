package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.elementssystem.AbilitiesID
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import io.papermc.paper.event.player.PlayerArmSwingEvent
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class EL_Lightning : BaseElement() {

    private val baseLightningAttackParticles = ParticleBuilder(Particle.ELECTRIC_SPARK).count(10).offset(0.5, 0.5, 0.5).extra(0.0).force(true).allPlayers()

    // Ability 1: SMITE
    @EventHandler
    fun abilityLightning(event: PlayerArmSwingEvent) {
        val player = event.player

        // Check if the event is specifically for the main hand and empty
        if (event.hand != EquipmentSlot.HAND || player.inventory.itemInMainHand.type != Material.AIR) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.LIGHTNING)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.LIGHTNING, AbilitiesID.SMITE.id)) { return }

        // Check cooldown
        val cooldownName = "lightning_ability_1"
        val durationCooldown = 30
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Lightning
        val maxDistance = 500
        val lineOfSight = player.getLineOfSight(null, maxDistance)
        val targetBlock = lineOfSight.firstOrNull { !it.type.isAir && it.type.isBlock }
        val distanceToTarget = lineOfSight.indexOf(targetBlock) // Calculate the distance to the target block

        // Summon lightning at the target
        val targetLocation = if (distanceToTarget <= maxDistance) {
            targetBlock?.location?.add(0.5, 0.0, 0.5)
        } else {
            player.eyeLocation.add(player.eyeLocation.direction.multiply(maxDistance.toDouble()))
        }

        // Check if targetLocation is null
        targetLocation?.let {
            // Summon particles
            baseLightningAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

            // Summon lightning at the target block
            object : BukkitRunnable() {
                override fun run() {
                    it.world?.strikeLightning(it)
                }
            }.runTaskLater(JavaPlugin.getPlugin(NinjagoMC::class.java), 1)

            // Update the cooldown for the player
            updateCooldown(player, cooldownName, durationCooldown)
        }
    }
}