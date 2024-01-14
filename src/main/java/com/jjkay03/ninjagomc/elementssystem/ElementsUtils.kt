package com.jjkay03.ninjagomc.elementssystem

import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.entity.Player

class ElementsUtils {
    companion object {
        // Fuction that checks if a players has an element
        fun hasElement(player: Player, element_id: ElementsID): Boolean {
            val ninjagoPlayer = NinjagoPlayer.get(player.uniqueId)
            if (ninjagoPlayer.elements_list.contains(element_id)) {
                return true
            } else {
                return false
            }
        }
    }
}