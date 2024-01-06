package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class NinjagoMCCommand() : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {

        sender.sendMessage("${NinjagoMC.PREFIX} §aNinjagoMC is running!")

        return true
    }
}