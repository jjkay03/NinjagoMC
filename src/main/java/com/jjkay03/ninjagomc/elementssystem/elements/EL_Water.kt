package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.elementssystem.AbilitiesID
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class EL_Water : BaseElement() {

    private val baseWaterAttackParticles = ParticleBuilder(Particle.WATER_SPLASH).count(20).offset(0.5, 0.5, 0.5).extra(0.0).force(true).allPlayers()

    // Ability 1: MERMAID
    @EventHandler
    fun onSneakToggle(event: PlayerToggleSneakEvent) {
        val player = event.player

        // Check if the player is sneaking (going down not up)
        if (player.isSneaking) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.WATER)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.WATER, AbilitiesID.MERMAID.id)) { return }

        // Check cooldown
        val cooldownName = "water_ability_1"
        val durationCooldown = 480
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.BLOCK_CONDUIT_ACTIVATE, 1.0f, 1.0f)

        // Summon particles
        ParticleBuilder(Particle.BUBBLE_POP)
            .count(25).offset(0.5, 0.5, 0.5).extra(0.0).force(true).allPlayers()
            .location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Apply effects
        player.addPotionEffect(PotionEffect(PotionEffectType.CONDUIT_POWER, 6000, 9, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.DOLPHINS_GRACE, 2400, 0, false, false))

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }
}