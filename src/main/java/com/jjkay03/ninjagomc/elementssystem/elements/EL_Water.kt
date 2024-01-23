package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.elementssystem.AbilitiesID
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import io.papermc.paper.event.player.PlayerArmSwingEvent
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.EquipmentSlot
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

    // Ability 2: WATER SHOT
    // TODO: CHANGE ABILITY USE SOUND AND ADD MORE PARTICLES TO TRAIL!
    @EventHandler
    fun abilityFireShot(event: PlayerArmSwingEvent) {
        val player = event.player

        // Check if the event is specifically for the main hand and empty
        if (event.hand != EquipmentSlot.HAND || player.inventory.itemInMainHand.type != Material.AIR) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.WATER)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.WATER, AbilitiesID.WATER_SHOT.id)) { return }

        // Check cooldown
        val cooldownName = "water_ability_2"
        val durationCooldown = 5
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.ENTITY_DROWNED_DEATH_WATER, 1.0f, 1.0f)

        // Summon particles
        baseWaterAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Bullet
        val bulletTrailParticle = ParticleBuilder(Particle.WATER_SPLASH).count(3).offset(0.1, 0.1, 0.1).extra(0.0).force(true).allPlayers()
        val bulletLandingParticle = ParticleBuilder(Particle.BUBBLE_POP).count(5).extra(0.0).force(true).allPlayers()
        shootBullet(player, bulletTrailParticle,  bulletLandingParticle, ElementsID.WATER)

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }
}