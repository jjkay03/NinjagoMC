package com.jjkay03.ninjagomc.elementssystem.elements

import com.destroystokyo.paper.ParticleBuilder
import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import kotlin.math.cos
import kotlin.math.sin

open class BaseElement() : Listener{

    private val abilityCooldowns = mutableMapOf<String, Long>() // Cooldowns map
    private var spinjitzuPoint = 0

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
        if (player.hasPermission("ninjagomc.cooldown.bypass")) { return false }

        val durationCooldownMillis = durationCooldownSeconds * 500L
        val remainingCooldownMillis = lastUsage + durationCooldownMillis - currentTime
        val onCooldown = remainingCooldownMillis > 0

        if (onCooldown) {
            // Convert remaining time to minutes and seconds
            val remainingMinutes = remainingCooldownMillis / (60 * 1000)
            val remainingSeconds = (remainingCooldownMillis % (60 * 1000)) / 1000

            // Display remaining time in minutes and seconds if more than 60 seconds
            val remainingTime = if (remainingMinutes > 0) { "${remainingMinutes}m ${remainingSeconds}s" } else { "${remainingSeconds}s" }

            // Send cooldown message to the player with remaining time
            player.sendActionBar("§c⏳ $remainingTime")
        }

        return onCooldown
    }

    // Update the cooldown for the player for a specific ability
    fun updateCooldown(player: Player, cooldownName: String, durationCooldownSeconds: Int) {
        if (player.hasPermission("ninjagomc.cooldown.bypass")) { return } // Bypass cooldown permission
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

    // Spinjitzu
    fun spinjitzu(player: Player, duration: Int, particle: Particle) {
        val tickDuration = duration * 20

        object : BukkitRunnable() {
            var ticks = 0

            override fun run() {
                if (ticks >= tickDuration) {
                    cancel()
                    return
                }

                // Spawn spinning tornado particles around the player
                spinjitzuParticleTornado(player, particle, 2f, 4)
                spinjitzuParticleTornado(player, particle, 2.5f, 4)
                spinjitzuParticleTornado(player, particle, 3f, 4)

                ticks++
            }
        }.runTaskTimer(NinjagoMC.instance, 0L, 1L)
    }

    // Summon tornado of particles around a player
    fun spinjitzuParticleTornado(player: Player, particle: Particle, radius: Float, particlesPerTick: Int) {
        val location = player.location.clone()

        for (i in 0 until particlesPerTick) {
            val particleLocation: Location = location.clone()
            particleLocation.add(radius * cos(spinjitzuPoint.toDouble()), 1.0, radius * sin(spinjitzuPoint.toDouble()))

            spinjitzuPoint++
            if (spinjitzuPoint > 360) spinjitzuPoint = 0
            location.world.spawnParticle(particle, particleLocation, 0, 0.0, 0.0, 0.0, 0.0)
        }
    }

}