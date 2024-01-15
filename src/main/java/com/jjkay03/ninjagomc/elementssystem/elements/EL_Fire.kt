package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.elementssystem.ElementsUtils
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class EL_Fire : Listener {

    private val baseFireAttackParticles = ParticleBuilder(Particle.FLAME)
        .count(10)
        .offset(0.5, 0.5, 0.5)
        .extra(0.0)
        .force(true)
        .allPlayers()

    // Ability 1: Put entities on fire ability
    @EventHandler
    fun onInteractEntity(event: PlayerInteractEntityEvent) {
        val player = event.player

        // Check if player has element
        if (!ElementsUtils.hasElement(player, ElementsID.FIRE)) { return }

        // Check if the first slot of the hotbar is selected
        if (player.inventory.heldItemSlot != 0) { return }

        // Play the flint and steel sound
        player.world.playSound(player.location, Sound.ITEM_FLINTANDSTEEL_USE, 1.0f, 1.0f)

        // Summon fire particles around the player
        baseFireAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Set the entity on fire
        val entity: Entity = event.rightClicked
        entity.fireTicks = 100
    }

    // Ability 1: Flint and steel hand ability
    @EventHandler
    fun onRightClickBlock(event: PlayerInteractEvent) {
        val player = event.player

        // Check if player has element
        if (!ElementsUtils.hasElement(player, ElementsID.FIRE)) { return }

        // Check if the first slot of the hotbar is selected
        if (player.inventory.heldItemSlot != 0) { return }

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
                            // Play the flint and steel sound
                            player.world.playSound(player.location, Sound.ITEM_FLINTANDSTEEL_USE, 1.0f, 1.0f)

                            // Place fire particle at the block face
                            baseFireAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).count(2).spawn()

                            // Place fire block
                            targetBlock.type = Material.FIRE
                        }
                    }
                }
            }
        }
    }

}