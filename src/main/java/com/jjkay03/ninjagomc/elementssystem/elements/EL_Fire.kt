package com.jjkay03.ninjagomc.elementssystem.elements

import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class EL_Fire : Listener{

    @EventHandler
    fun onInteract(event: PlayerInteractEvent){
        val player = event.player
        val ninjagoPlayer = NinjagoPlayer.get(player.uniqueId)
        if (!ninjagoPlayer.elements_list.contains(ElementsID.FIRE)) {
            return
        }
        player.sendMessage("THIS WORKS!")

    }

}