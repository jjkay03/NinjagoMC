package com.jjkay03.ninjagomc.elementssystem.elements

import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener

open class BaseElement() : Listener{

    // Map to track player cooldowns (player UUID to last usage time)
    private val abilityCooldowns = mutableMapOf<String, Long>()

    // Function that checks selected hotkeys
    fun isHotkeySelected(player: Player, elementsID: ElementsID, ability: Int) : Boolean {
        val ninjagoPlayer = NinjagoPlayer.get(player.uniqueId)
        val selectedHotbarSlot = player.inventory.heldItemSlot
        val selectedHotkey = ninjagoPlayer.hotkey_list[selectedHotbarSlot]
        //Bukkit.getLogger().info("Selected Slot: $selectedHotbarSlot | Selected Hotkey: $selectedHotkey") // DEBUG
        return selectedHotkey.element == elementsID && selectedHotkey.ability == ability
    }

    // Check if the player is on cooldown for a specific ability
    fun isOnCooldown(player: Player, cooldownName: String, durationCooldown: Int): Boolean {
        val lastUsage = abilityCooldowns[player.uniqueId.toString() + cooldownName] ?: 0
        val currentTime = System.currentTimeMillis()

        val remainingCooldown = (lastUsage + durationCooldown - currentTime) / 1000
        val onCooldown = currentTime - lastUsage < durationCooldown

        if (onCooldown) {
            // Send cooldown message to the player with remaining time
            player.sendActionBar("§c⏳ ${remainingCooldown}s")
        }

        return onCooldown
    }

    // Update the cooldown for the player for a specific ability
    fun updateCooldown(player: Player, cooldownName: String, durationCooldown: Int) {
        abilityCooldowns[player.uniqueId.toString() + cooldownName] = System.currentTimeMillis() + durationCooldown
    }
}