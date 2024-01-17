package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.elementssystem.AbilitiesID
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
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

    // Ability 1: FROSTBITE (Freeze entity)
    @EventHandler
    fun abilityFrostbite(event: PlayerInteractEntityEvent) {
        val player = event.player
        val entity: Entity = event.rightClicked

        // Check if the event is specifically for the main hand
        if (event.hand != EquipmentSlot.HAND) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.ICE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.ICE, AbilitiesID.FROSTBITE.id)) { return }

        // Check cooldown and specify cooldown names and durations for the ability
        val cooldownName = "ice_ability_1"
        val durationCooldown = 10
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.ENTITY_PLAYER_HURT_FREEZE, 1.0f, 1.0f)

        // Summon particles
        baseIceAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()
        baseIceAttackParticles.location(entity.location.add(0.0, 1.0, 0.0)).count(5).spawn()

        // Freeze entity
        entity.freezeTicks = 500

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }
}
