// Import statements for necessary classes
package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.utility.PlayerData
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ElementsCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val elementsList = PlayerData.readElementsList(sender)

            if (elementsList.isNotEmpty()) {
                val playerElements = elementsList.mapNotNull { elementId ->
                    ElementsID.values().find { it.id.equals(elementId, ignoreCase = true) }
                }

                if (playerElements.isNotEmpty()) {
                    val elementLabels = playerElements.map { it.label }
                    sender.sendMessage("${NinjagoMC.PREFIX} §7Your elements are: ${elementLabels.joinToString("§7, ")}")
                } else {
                    sender.sendMessage("${NinjagoMC.PREFIX} §cNo matching elements found!")
                }
            } else {
                sender.sendMessage("${NinjagoMC.PREFIX} §cYour have no elements!")
            }
        } else {
            sender.sendMessage("§cOnly players can execute this command")
        }

        return true
    }
}
