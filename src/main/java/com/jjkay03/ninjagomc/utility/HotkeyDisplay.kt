package com.jjkay03.ninjagomc.utility

import com.jjkay03.ninjagomc.elementssystem.AbilitiesID
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent

class HotkeyDisplay : Listener {

    @EventHandler
    fun onHotbarSwitch(event: PlayerItemHeldEvent) {
        val player: Player = event.player
        val ninjagoPlayer: NinjagoPlayer = NinjagoPlayer.get(player.uniqueId)
        val selectedSlot: Int = event.newSlot // Get the selected hotbar slot

        // Check if the selected hotbar slot has a bound ability
        val hotkey: NinjagoPlayer.Hotkey = ninjagoPlayer.hotkey_list[selectedSlot]

        // Check if display-actionbar is set to true
        if (ninjagoPlayer.displayActionbar && hotkey.ability != null) {
            // Find the ability with the given id
            val ability: AbilitiesID? = AbilitiesID.entries.find { it.id == hotkey.ability && it.elementsID == hotkey.element }

            // Check if the ability is found and the player has the required element
            if (ability != null && ninjagoPlayer.elements_list.any { it == ability.elementsID }) {
                player.sendActionBar("${ability.elementsID.label}${ability.elementsID.label.take(2)}: ${ability.label}")
            }
        }
    }
}
