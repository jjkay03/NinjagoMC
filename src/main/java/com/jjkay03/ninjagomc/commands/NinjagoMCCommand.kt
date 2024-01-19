package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class NinjagoMCCommand() : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage("${NinjagoMC.PREFIX}§a§l§m---------------------------------------------")
        sender.sendMessage("${NinjagoMC.PREFIX} ")
        sender.sendMessage("${NinjagoMC.PREFIX}§2§lNinjagoMC is running!")
        sender.sendMessage("${NinjagoMC.PREFIX}§7Ninjago elements in Minecraft")
        sender.sendMessage("${NinjagoMC.PREFIX} ")
        sender.sendMessage("${NinjagoMC.PREFIX}§7⏵ Version: §a")
        sender.sendMessage("${NinjagoMC.PREFIX}§7⏵ Author: §6jjkay03")
        sender.sendMessage("${NinjagoMC.PREFIX} ")
        sender.sendMessage("${NinjagoMC.PREFIX}§7⏵ Modrinth: §ehttps://modrinth.com/plugin/ninjagomc")
        sender.sendMessage("${NinjagoMC.PREFIX}§7⏵ GitHub: §ehttps://github.com/jjkay03/NinjagoMC")
        sender.sendMessage("${NinjagoMC.PREFIX} ")
        sender.sendMessage("${NinjagoMC.PREFIX}§a§l§m---------------------------------------------")
        return true
    }
}