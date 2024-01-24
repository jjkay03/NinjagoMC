package com.jjkay03.ninjagomc.elementssystem.elements

import com.jjkay03.ninjagomc.elementssystem.AbilitiesID
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerToggleSneakEvent

class EL_Earth : BaseElement() {

    // Ability 1: SPINJITZU
    @EventHandler
    fun abilityEarthSpinjitzu(event: PlayerToggleSneakEvent) {
        val player = event.player

        // Check if the player is sneaking (going down not up)
        if (player.isSneaking) { return }

        // Check if player has element
        if (!NinjagoPlayer.hasElement(player, ElementsID.EARTH)) { return }

        // Check if the first slot of the hotbar is selected
        if (!isHotkeySelected(player, ElementsID.EARTH, AbilitiesID.EARTH_SPINJITZU.id)) { return }

        // Check cooldown
        val cooldownName = AbilitiesID.EARTH_SPINJITZU.toString()
        val durationCooldown = 600
        if (isOnCooldown(player, cooldownName, durationCooldown)) { return }

        // Spinjitzu
        spinjitzu(player, 30, 2.0, Particle.CAMPFIRE_COSY_SMOKE, ElementsID.EARTH)

        // Update the cooldown for the player
        updateCooldown(player, cooldownName, durationCooldown)
    }

}