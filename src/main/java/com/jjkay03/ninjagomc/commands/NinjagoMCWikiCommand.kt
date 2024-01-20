package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class NinjagoMCWikiCommand() : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        sender.sendMessage("${NinjagoMC.PREFIX}§aYou can find all the info about the NinjagoMC on the wiki:")
        sender.sendMessage("${NinjagoMC.PREFIX}§e${NinjagoMCCommand.WIKI_LINK}")
        return true
    }
}