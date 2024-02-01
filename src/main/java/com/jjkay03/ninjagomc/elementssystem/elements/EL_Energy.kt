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

class EL_Energy: BaseElement() {

    private val baseEnergyAttackParticles = ParticleBuilder(Particle.SNEEZE).count(10).offset(0.5, 0.5, 0.5).extra(0.0).force(true).allPlayers()

    // Ability 1: SPINJITZU
    @EventHandler
    fun abilityEnergySpinjitzu(event: PlayerToggleSneakEvent) {
        val player = event.player

        // Check if the player is sneaking (going down not up)
        if (player.isSneaking) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.ENERGY)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.ENERGY, AbilitiesID.ENERGY_SPINJITZU.id)) { return }

        // Check cooldown
        val cooldownName = AbilitiesID.ENERGY_SPINJITZU.toString()
        val durationCooldown = 600
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Spinjitzu
        spinjitzu(player, 30, 2.0, Particle.SNEEZE, ElementsID.ENERGY)

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }

    // Ability 2: ENERGY SHOT
    @EventHandler
    fun abilityEnergyShot(event: PlayerArmSwingEvent) {
        val player = event.player

        // Check if the event is specifically for the main hand
        if (event.hand != EquipmentSlot.HAND) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.ENERGY)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.ENERGY, AbilitiesID.ENERGY_SHOT.id)) { return }

        // Check cooldown
        val cooldownName = AbilitiesID.ENERGY_SHOT.toString()
        val durationCooldown = 3
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.ENTITY_SHULKER_BULLET_HURT, 1.0f, 1.0f)

        // Summon particles
        baseEnergyAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Bullet
        val bulletTrailParticle = ParticleBuilder(Particle.SNEEZE).count(3).offset(0.1, 0.1, 0.1).extra(0.0).force(true).allPlayers()
        val bulletLandingParticle = ParticleBuilder(Particle.SLIME).count(5).extra(0.0).force(true).allPlayers()
        shootBullet(player, bulletTrailParticle,  bulletLandingParticle, ElementsID.ENERGY)

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }

    // Ability 3: ENERGY_CHARGE
    @EventHandler
    fun abilityEnergyCharge(event: PlayerToggleSneakEvent) {
        val player = event.player

        // Check if the player is sneaking (going down not up)
        if (player.isSneaking) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.ENERGY)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.ENERGY, AbilitiesID.ENERGY_CHARGE.id)) { return }

        // Check cooldown
        val cooldownName = AbilitiesID.ENERGY_CHARGE.toString()
        val durationCooldown = 300
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.ENTITY_SHULKER_BULLET_HURT, 1.0f, 1.0f)

        // Summon particles
        baseEnergyAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Apply the fire resistance
        player.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 200, 0, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 1200, 1, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.JUMP, 1200, 4, false, false))

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }
}