package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.elementssystem.ElementsUtils
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.EquipmentSlot

class EL_Ice : BaseElement() {

    private val baseIceAttackParticles = ParticleBuilder(Particle.SNOWFLAKE)
        .count(10)
        .offset(0.5, 0.5, 0.5)
        .extra(0.0)
        .force(true)
        .allPlayers()

    // Ability 1: Freeze (Freeze entity)
    @EventHandler
    fun onInteractEntity(event: PlayerInteractEntityEvent) {
        val player = event.player

        // Check if the event is specifically for the main hand
        if (event.hand != EquipmentSlot.HAND) { return }

        // Check if player has element
        if (!ElementsUtils.hasElement(player, ElementsID.ICE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.ICE, 1)) { return }

        // Check cooldown and specify cooldown names and durations for the ability
        val cooldownName = "ice_ability_1"
        val durationCooldown = 5000
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Play the flint and steel sound
        player.world.playSound(player.location, Sound.ENTITY_PLAYER_HURT_FREEZE, 1.0f, 1.0f)

        // Summon fire particles around the player
        baseIceAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Set the entity on fire
        val entity: Entity = event.rightClicked
        entity.freezeTicks = 500

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }
}
