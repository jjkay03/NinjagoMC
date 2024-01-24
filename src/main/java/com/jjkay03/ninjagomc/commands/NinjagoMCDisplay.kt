package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class NinjagoMCDisplay : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        // Check if the command sender is a player
        if (sender !is Player) {
            sender.sendMessage("${NinjagoMC.PREFIX}§cOnly players can use this command!")
            return true
        }

        // Check if the command has the correct number of arguments
        if (args == null || args.size != 1) {
            sender.sendMessage("${NinjagoMC.PREFIX}§cUsage: /ninjagodisplay <actionbar / scoreboard>")
            return true
        }

        // Get the player and their NinjagoPlayer data
        val player = sender
        val ninjagoPlayer = NinjagoPlayer.get(player.uniqueId)

        // Check if the provided argument is valid
        val displayType = args[0].lowercase()
        if (displayType != "actionbar" && displayType != "scoreboard") {
            sender.sendMessage("${NinjagoMC.PREFIX}§cInvalid display type!")
            return true
        }

        // Toggle the display key
        when (displayType) {
            "actionbar" -> {
                ninjagoPlayer.displayActionbar = !ninjagoPlayer.displayActionbar
                sender.sendMessage("${NinjagoMC.PREFIX}§aToggled actionbar to ${ninjagoPlayer.displayActionbar}")
            }
            "scoreboard" -> {
                ninjagoPlayer.displayScoreboard = !ninjagoPlayer.displayScoreboard
                sender.sendMessage("${NinjagoMC.PREFIX}§aToggled scoreboard to ${ninjagoPlayer.displayScoreboard}")
            }
        }

        // Save the player data
        ninjagoPlayer.save()

        return true
    }

    // Tab completion logic for the command
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        if (args.size == 1) {
            // If the argument is the display type
            return listOf("actionbar", "scoreboard")
                .filter { it.startsWith(args[0], ignoreCase = true) }
        }
        return emptyList()
    }
}
