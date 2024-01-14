package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.elementssystem.ElementsUtils
import org.bukkit.Particle
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent

class EL_Fire : Listener {

    private val baseFireAttackParticles = ParticleBuilder(Particle.FLAME)
        .count(10)
        .offset(0.5, 0.5, 0.5)
        .extra(0.0)
        .force(true)
        .allPlayers()

    // Set entity on fire when hitting it
    @EventHandler
    fun onInteractEntity(event: PlayerInteractEntityEvent) {
        val player = event.player

        // Check if player has element
        if (!ElementsUtils.hasElement(player, ElementsID.FIRE)) { return }

        // Summon fire particles around the player
        baseFireAttackParticles.location(player.location.add(0.0, 1.0, 0.0)).spawn()

        // Set the entity on fire
        val entity: Entity = event.rightClicked
        entity.fireTicks = 100
    }

}