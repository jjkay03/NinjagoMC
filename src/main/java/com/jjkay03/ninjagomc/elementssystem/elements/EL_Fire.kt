package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.elementssystem.AbilitiesID
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.Arrow
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class EL_Fire : BaseElement() {

    private val baseFireAttackParticles = ParticleBuilder(Particle.FLAME)
        .count(10)
        .offset(0.5, 0.5, 0.5)
        .extra(0.0)
        .force(true)
        .allPlayers()

    // Ability 1: IGNITE (Put entities on fire)
    @EventHandler
    fun abilityIgnite1(event: PlayerInteractEntityEvent) {
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

    // Ability 1: IGNITE (Flint and steel hand)
    @EventHandler
    fun abilityIgnite2(event: PlayerInteractEvent) {
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
                            baseFireAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).count(2).spawn()

                            // Place fire block
                            targetBlock.type = Material.FIRE
                        }
                    }
                }
            }
        }
    }

    // Ability 2: FIRE SHOT
    @EventHandler
    fun abilityFireShot(event: PlayerInteractEvent) {
        val player = event.player

        // Check if the event is specifically for the main hand and left click air
        if (event.hand != EquipmentSlot.HAND && event.action == Action.LEFT_CLICK_AIR) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.FIRE)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.FIRE, AbilitiesID.FIRE_SHOT.id)) { return }

        // Play sound
        player.world.playSound(player.location, Sound.ENTITY_PLAYER_HURT_ON_FIRE, 1.0f, 1.0f)

        // Summon particles
        baseFireAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Arrow
        val arrow: Arrow = player.launchProjectile(Arrow::class.java)
        arrow.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
        arrow.velocity = player.location.direction.multiply(2.0)
        //Bukkit.getOnlinePlayers().forEach { it.hideEntity(NinjagoMC.instance, arrow) }

    }

}