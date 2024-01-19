package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class NinjagoMCCommand() : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        val modrinthLink = "https://modrinth.com/plugin/ninjagomc"
        val githubLink = "https://github.com/jjkay03/NinjagoMC"

        sender.sendMessage("${NinjagoMC.PREFIX}§a§l§m---------------------------------------------")
        sender.sendMessage("${NinjagoMC.PREFIX} ")
        sender.sendMessage("${NinjagoMC.PREFIX}§2§lNinjagoMC is running!")
        sender.sendMessage("${NinjagoMC.PREFIX}§7Ninjago elements in Minecraft")
        sender.sendMessage("${NinjagoMC.PREFIX} ")
        sender.sendMessage("${NinjagoMC.PREFIX}§7⏵ Version: §a${NinjagoMC.instance.description.version}")
        sender.sendMessage("${NinjagoMC.PREFIX}§7⏵ Author: §6jjkay03")
        sender.sendMessage("${NinjagoMC.PREFIX} ")
        sender.sendMessage("${NinjagoMC.PREFIX}§7⏵ Modrinth: §e${modrinthLink}")
        sender.sendMessage("${NinjagoMC.PREFIX}§7⏵ GitHub: §e${githubLink}")
        sender.sendMessage("${NinjagoMC.PREFIX} ")
        sender.sendMessage("${NinjagoMC.PREFIX}§a§l§m---------------------------------------------")
        return true
    }
}