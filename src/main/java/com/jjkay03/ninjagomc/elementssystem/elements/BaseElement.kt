package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitTask

open class BaseElement() : Listener{

    // Map to track player cooldowns (player UUID to last usage time)
    private val abilityCooldowns = mutableMapOf<String, Long>()

    // Checks selected hotkeys
    fun isHotkeySelected(player: Player, elementsID: ElementsID, ability: Int) : Boolean {
        val ninjagoPlayer = NinjagoPlayer.get(player.uniqueId)
        val selectedHotbarSlot = player.inventory.heldItemSlot
        val selectedHotkey = ninjagoPlayer.hotkey_list[selectedHotbarSlot]
        //Bukkit.getLogger().info("Selected Slot: $selectedHotbarSlot | Selected Hotkey: $selectedHotkey") // DEBUG
        return selectedHotkey.element == elementsID && selectedHotkey.ability == ability
    }

    // Check if the player is on cooldown for a specific ability
    fun isOnCooldown(player: Player, cooldownName: String, durationCooldownSeconds: Int): Boolean {
        val lastUsage = abilityCooldowns[player.uniqueId.toString() + cooldownName] ?: 0
        val currentTime = System.currentTimeMillis()

        // Bypass cooldown permission
        if (player.hasPermission("ninjagomc.cooldown.bypass")){ return false}

        val durationCooldownMillis = durationCooldownSeconds * 500L
        val remainingCooldown = (lastUsage + durationCooldownMillis - currentTime) / 1000
        val onCooldown = currentTime - lastUsage < durationCooldownMillis

        if (onCooldown) {
            // Send cooldown message to the player with remaining time
            player.sendActionBar("§c⏳ ${remainingCooldown}s")
        }

        return onCooldown
    }

    // Update the cooldown for the player for a specific ability
    fun updateCooldown(player: Player, cooldownName: String, durationCooldownSeconds: Int) {
        val durationCooldownMillis = durationCooldownSeconds * 500L
        abilityCooldowns[player.uniqueId.toString() + cooldownName] = System.currentTimeMillis() + durationCooldownMillis
    }

    // Shoot bullet
    fun shootBullet(player: Player, trailParticle: ParticleBuilder, landingParticle: ParticleBuilder, elementsID: ElementsID){
        val arrow: Arrow = player.launchProjectile(Arrow::class.java)
        arrow.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED // Can't pick up arrow
        arrow.velocity = player.location.direction.multiply(2.0) // Shoot arrow

        //Element specific
        if (elementsID == ElementsID.FIRE) { arrow.fireTicks = Int.MAX_VALUE } // Set arrow on fire

        // Make arrow invisible
        Bukkit.getOnlinePlayers().forEach { it.hideEntity(NinjagoMC.instance, arrow) }

        // Schedule a task to check for arrow landing
        var task: BukkitTask? = null
        task = Bukkit.getScheduler().runTaskTimer(NinjagoMC.instance, Runnable {
            if (!arrow.isValid || arrow.isOnGround) {
                landingParticle.location(arrow.location).spawn()
                arrow.remove()
                task?.cancel()
            }
            else{
                trailParticle.location(arrow.location).spawn()
            }
        }, 0L, 1L)
    }

}