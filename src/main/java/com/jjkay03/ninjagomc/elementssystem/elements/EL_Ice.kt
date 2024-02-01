package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.elementssystem.AbilitiesID
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import io.papermc.paper.event.player.PlayerArmSwingEvent
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.EquipmentSlot

class EL_Ice : BaseElement() {

    private val baseIceAttackParticles = ParticleBuilder(Particle.SNOWFLAKE).count(10).offset(0.5, 0.5, 0.5).extra(0.0).force(true).allPlayers()

    // Ability 1: SPINJITZU
    @EventHandler
    fun abilityIceSpinjitzu(event: PlayerToggleSneakEvent) {
        val player = event.player

        // Check if the player is sneaking (going down not up)
        if (player.isSneaking) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.ICE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.ICE, AbilitiesID.FIRE_SPINJITZU.id)) { return }

        // Check cooldown
        val cooldownName = AbilitiesID.FIRE_SPINJITZU.toString()
        val durationCooldown = 600
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Spinjitzu
        spinjitzu(player, 30, 2.0, Particle.SNOWFLAKE, ElementsID.ICE)

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }

    // Ability 2: FROSTBITE (Freeze entity)
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

        // Check cooldown
        val cooldownName = AbilitiesID.FROSTBITE.toString()
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

    // Ability 3: ICE SHOT
    @EventHandler
    fun abilityIceShot(event: PlayerArmSwingEvent) {
        val player = event.player

        // Check if the event is specifically for the main hand
        if (event.hand != EquipmentSlot.HAND) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.ICE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.ICE, AbilitiesID.ICE_SHOT.id)) { return }

        // Check cooldown
        val cooldownName = AbilitiesID.ICE_SHOT.toString()
        val durationCooldown = 3
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.ENTITY_PLAYER_HURT_FREEZE, 1.0f, 1.0f)

        // Summon particles
        baseIceAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Bullet
        val bulletTrailParticle = ParticleBuilder(Particle.SNOWFLAKE).count(3).offset(0.1, 0.1, 0.1).extra(0.0).force(true).allPlayers()
        val bulletLandingParticle = ParticleBuilder(Particle.SNOW_SHOVEL).count(5).extra(0.0).force(true).allPlayers()
        shootBullet(player, bulletTrailParticle,  bulletLandingParticle, ElementsID.ICE)

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }
}
