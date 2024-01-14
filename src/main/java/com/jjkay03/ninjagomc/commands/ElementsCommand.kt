// Import statements for necessary classes
package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.utility.PlayerData
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ElementsCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            val targetPlayer: Player? = if (args.isNotEmpty()) {
                Bukkit.getPlayer(args[0]) ?: Bukkit.getOfflinePlayer(args[0]).player
            } else {
                sender
            }

            targetPlayer?.let {
                val elementsList = PlayerData.readElementsList(targetPlayer)

                if (elementsList.isNotEmpty()) {
                    val playerElements = elementsList.mapNotNull { elementId ->
                        ElementsID.values().find { it.id.equals(elementId, ignoreCase = true) }
                    }

                    if (playerElements.isNotEmpty()) {
                        val elementLabels = playerElements.map { it.label }
                        sender.sendMessage("${NinjagoMC.PREFIX} §7Elements of ${targetPlayer.name}: ${elementLabels.joinToString("§7, ")}")
                    } else {
                        sender.sendMessage("${NinjagoMC.PREFIX} §cNo matching elements found for ${targetPlayer.name}!")
                    }
                } else {
                    sender.sendMessage("${NinjagoMC.PREFIX} §c${targetPlayer.name} has no elements!")
                }
            } ?: run {
                sender.sendMessage("${NinjagoMC.PREFIX} §cPlayer not found!")
            }
        } else {
            sender.sendMessage("§cOnly players can execute this command")
        }

        return true
    }
}
