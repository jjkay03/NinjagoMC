package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ElementsListCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            sender.sendMessage("${NinjagoMC.PREFIX} §a§nELEMENTS LIST:")

            ElementsID.values().forEach { element ->
                val implementedStatus = if (element.implemented) "§a✔" else "§c❌"
                val elementInfo = "$implementedStatus ${element.label} §7(ID: ${element.id} / TYPE: ${element.type})"
                sender.sendMessage(elementInfo)
            }
        } else {
            sender.sendMessage("Only players can execute this command.")
        }

        return true
    }
}
