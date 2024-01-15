package com.jjkay03.ninjagomc.elementssystem.elements

import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener

open class BaseElement() : Listener{
    fun isHotkeySelected(player: Player, elementsID: ElementsID, ability: Int) : Boolean {
        val ninjagoPlayer = NinjagoPlayer.get(player.uniqueId)
        val selectedHotbarSlot = player.inventory.heldItemSlot
        val selectedHotkey = ninjagoPlayer.hotkey_list[selectedHotbarSlot]

        //Bukkit.getLogger().info("Selected Slot: $selectedHotbarSlot | Selected Hotkey: $selectedHotkey") // DEBUG
        return selectedHotkey.element == elementsID && selectedHotkey.ability == ability
    }
}