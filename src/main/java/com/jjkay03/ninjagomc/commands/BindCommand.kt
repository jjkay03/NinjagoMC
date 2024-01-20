package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.elementssystem.AbilitiesID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class BindCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        // Check if the command has the correct number of arguments
        if (args == null || args.isEmpty() || args.size > 1) {
            sender.sendMessage("${NinjagoMC.PREFIX}§cUsage: /bind <ABILITY / CLEAR>")
            return true
        }

        // Check if the command sender is a player
        if (sender !is Player) {
            sender.sendMessage("${NinjagoMC.PREFIX}§cOnly players can use this command!")
            return true
        }

        // Get the player and their NinjagoPlayer data
        val player = sender
        val ninjagoPlayer = NinjagoPlayer.get(player.uniqueId)

        if (args[0].equals("CLEAR", ignoreCase = true)) {
            // Clear the hotkey-list
            for (i in 0 until 9) {
                ninjagoPlayer.hotkey_list[i] = NinjagoPlayer.Hotkey(null, -1)
            }
            sender.sendMessage("${NinjagoMC.PREFIX}§aCleared all hotkeys")
        } else {
            // Bind the ability
            val abilityName = args[0].uppercase()

            // Check if the provided ability ID is valid
            if (AbilitiesID.entries.none { it.name == abilityName }) {
                sender.sendMessage("${NinjagoMC.PREFIX}§cInvalid ability!")
                return true
            }

            // Get the ability and the player's current element
            val ability = AbilitiesID.valueOf(abilityName)
            val hasValidAbility = ninjagoPlayer.elements_list.any { it == ability.elementsID }
            val element = ability.elementsID

            // Check if the player has any valid abilities for the given element
            if (!hasValidAbility) {
                sender.sendMessage("${NinjagoMC.PREFIX}§cYou don't have the required element for this ability!")
                return true
            }

            // Get the player's currently selected hotbar slot
            val hotbarSlot = player.inventory.heldItemSlot

            // Update the hotkey_list with the new Hotkey instance
            ninjagoPlayer.hotkey_list[hotbarSlot] = NinjagoPlayer.Hotkey(element, ability.id)

            // Inform the player about the successful binding
            val shortLabel = element.label.take(2)
            sender.sendMessage("${NinjagoMC.PREFIX}§aBound ${element.label}§r$shortLabel: ${ability.label} §ato your selected hotbar slot (${hotbarSlot + 1})")
        }

        // Save the player data
        ninjagoPlayer.save()

        return true
    }

    // Tab completion logic for the command
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        // Check if the command sender is a player
        if (sender !is Player) {
            return emptyList()
        }

        // Get the player and their NinjagoPlayer data
        val player = sender
        val ninjagoPlayer = NinjagoPlayer.get(player.uniqueId)

        if (args.size == 1) {
            // If the argument is the ability name
            return listOf("CLEAR") + AbilitiesID.entries
                .filter { it.name.startsWith(args[0], ignoreCase = true) }
                .filter { elementId -> ninjagoPlayer.elements_list.any { it == elementId.elementsID } }
                .map { it.name }
        }
        return emptyList()
    }
}
