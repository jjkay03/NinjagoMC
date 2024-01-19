package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.TextColor
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ElementsListCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            sender.sendMessage("${NinjagoMC.PREFIX}§a§nELEMENTS LIST:")

            ElementsID.entries.forEach { element ->
                val implementedStatus = if (element.implemented) ChatColor.GREEN.toString() + "✔" else ChatColor.RED.toString() + "❌"
                val elementInfo = "$implementedStatus ${element.label}"

                // Create hover text with ID and TYPE on separate lines
                val hoverText = Component.text()
                    .append(Component.text("ID: ${element.id}\n").color(TextColor.color(128, 128, 128)))
                    .append(Component.text("TYPE: ${element.type}").color(TextColor.color(128, 128, 128)))
                    .build()

                // Combine the message with the hover text
                val finalMessage = Component.text(elementInfo).hoverEvent(HoverEvent.showText(hoverText))

                // Send the message
                sender.sendMessage(finalMessage)
            }
        } else {
            sender.sendMessage("Only players can execute this command.")
        }

        return true
    }
}
