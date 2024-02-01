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
import org.bukkit.entity.Fireball
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class EL_Fire : BaseElement() {

    private val baseFireAttackParticles = ParticleBuilder(Particle.FLAME).count(10).offset(0.5, 0.5, 0.5).extra(0.0).force(true).allPlayers()

    // Ability 1: SPINJITZU
    @EventHandler
    fun abilityFireSpinjitzu(event: PlayerToggleSneakEvent) {
        val player = event.player

        // Check if the player is sneaking (going down not up)
        if (player.isSneaking) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.FIRE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.FIRE, AbilitiesID.FIRE_SPINJITZU.id)) { return }

        // Check cooldown
        val cooldownName = AbilitiesID.FIRE_SPINJITZU.toString()
        val durationCooldown = 600
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Spinjitzu
        spinjitzu(player, 30, 2.0, Particle.FLAME, ElementsID.FIRE)

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }

    // Ability 2: IGNITE (Entity)
    @EventHandler
    fun abilityIgniteEntity(event: PlayerInteractEntityEvent) {
        val player = event.player
        val entity: Entity = event.rightClicked

        // Check if the event is specifically for the main hand
        if (event.hand != EquipmentSlot.HAND) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.FIRE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.FIRE, AbilitiesID.IGNITE.id)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.ITEM_FLINTANDSTEEL_USE, 1.0f, 1.0f)

        // Summon particles
        baseFireAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Set the entity on fire
        entity.fireTicks = 100
    }

    // Ability 2: IGNITE (Block)
    @EventHandler
    fun abilityIgniteBlock(event: PlayerInteractEvent) {
        val player = event.player

        // Check if the event is specifically for the main hand
        if (event.hand != EquipmentSlot.HAND) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.FIRE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.FIRE, AbilitiesID.IGNITE.id)) { return }

        // Check if the action is a right-click on a block
        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            // Check if the player's hand is empty
            if (player.inventory.itemInMainHand.type == Material.AIR) {
                val clickedBlock = event.clickedBlock
                val blockFace = event.blockFace
                // Check if the event has a valid clicked block
                if (clickedBlock != null) {
                    // Check if the clicked block is not an inventory block (chest, item frame...)
                    if (!clickedBlock.type.isInteractable) {
                        // Check if the target block is air before placing fire block
                        val targetBlock = clickedBlock.getRelative(blockFace)
                        if (targetBlock.type == Material.AIR) {
                            // Play sound
                            player.world.playSound(player.location, Sound.ITEM_FLINTANDSTEEL_USE, 1.0f, 1.0f)

                            // Summon particles
                            baseFireAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

                            // Place fire block
                            targetBlock.type = Material.FIRE
                        }
                    }
                }
            }
        }
    }

    // Ability 3: FIRE SHOT
    @EventHandler
    fun abilityFireShot(event: PlayerArmSwingEvent) {
        val player = event.player

        // Check if the event is specifically for the main hand
        if (event.hand != EquipmentSlot.HAND) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.FIRE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.FIRE, AbilitiesID.FIRE_SHOT.id)) { return }

        // Check cooldown
        val cooldownName = AbilitiesID.FIRE_SHOT.toString()
        val durationCooldown = 5
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.ENTITY_PLAYER_HURT_ON_FIRE, 1.0f, 1.0f)

        // Summon particles
        baseFireAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Bullet
        val bulletTrailParticle = ParticleBuilder(Particle.FLAME).count(3).offset(0.1, 0.1, 0.1).extra(0.0).force(true).allPlayers()
        val bulletLandingParticle = ParticleBuilder(Particle.LAVA).count(5).extra(0.0).force(true).allPlayers()
        shootBullet(player, bulletTrailParticle,  bulletLandingParticle, ElementsID.FIRE)

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }

    // Ability 4: FIREPROOF
    @EventHandler
    fun abilityFirreproof(event: PlayerToggleSneakEvent) {
        val player = event.player

        // Check if the player is sneaking (going down not up)
        if (player.isSneaking) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.FIRE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.FIRE, AbilitiesID.FIREPROOF.id)) { return }

        // Check cooldown
        val cooldownName = AbilitiesID.FIREPROOF.toString()
        val durationCooldown = 120
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.ITEM_FIRECHARGE_USE, 1.0f, 1.0f)

        // Summon particles
        ParticleBuilder(Particle.WAX_ON)
            .count(10).offset(0.5, 0.5, 0.5).extra(0.0).force(true).allPlayers()
            .location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Apply the fire resistance
        player.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1200, 9, false, false))

        // If player is on fire
        if (player.fireTicks > 0) {
            player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f, 1.0f)
            player.fireTicks = 0
        }

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }

    // Ability 5: FIREBALL
    @EventHandler
    fun abilityFireball(event: PlayerArmSwingEvent) {
        val player = event.player

        // Check if the event is specifically for the main hand
        if (event.hand != EquipmentSlot.HAND) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.FIRE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.FIRE, AbilitiesID.FIREBALL.id)) { return }

        // Check cooldown
        val cooldownName = AbilitiesID.FIREBALL.toString()
        val durationCooldown = 120
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_SHOOT, 1.0f, 1.0f)

        // Summon particles
        baseFireAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Shoot fireball
        val fireball: Fireball = player.launchProjectile(Fireball::class.java)
        fireball.velocity = player.location.direction.multiply(2.0)
        fireball.shooter = player

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }


}