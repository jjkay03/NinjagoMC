package com.jjkay03.ninjagomc.commands

import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import com.jjkay03.ninjagomc.utility.NinjagoPlayer
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class SetElementsCommand: CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>?): Boolean {
        if (args == null || args.size < 2){
            sender.sendMessage("${NinjagoMC.PREFIX}§cUsage: /setelements <PLAYER> <ELEMENT ID / CLEAR>")
            return true
        }

        val player = Bukkit.getOfflinePlayer(args.first())
        val ninjagoPlayer = NinjagoPlayer.get(player.uniqueId)

        if (args.get(1).lowercase() == "clear"){
            ninjagoPlayer.elements_list.clear()
            sender.sendMessage("${NinjagoMC.PREFIX}§aCleared all of ${player.name}'s elements")
        }
        else if (ElementsID.entries.any{it.id == args.get(1).uppercase()}) {
            ninjagoPlayer.elements_list.add(ElementsID.valueOf(args.get(1).uppercase()))
            sender.sendMessage("${NinjagoMC.PREFIX}§aAdded ${ninjagoPlayer.elements_list.last().label} §aelement to ${player.name}")

            // Send message to player getting element
            if (player.isOnline) {
                val titleText = ninjagoPlayer.elements_list.last().label
                val subTitleText = "§7Use this powers wisely"
                player.player?.sendTitle(titleText, subTitleText, 10, 60, 10)
                player.player?.playSound(player.player!!.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.5f)
            }
        }
        else
            sender.sendMessage("${NinjagoMC.PREFIX}§cInvalid element ID!")

        ninjagoPlayer.save()

        return true
    }

    // Manage command tab complete options
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): List<String> {
        return if (args == null || args.size < 2)
            return Bukkit.getOnlinePlayers()
                .map { it.name }
                .filter {
                    if (args?.isEmpty() != false) true
                    else it.lowercase().startsWith(args[0].lowercase())
                }
                .toMutableList()
        else if (args.size < 3) {
            val elementList = mutableListOf("clear")
            elementList.addAll(ElementsID.entries.map { it.toString() }.toMutableList())
            return elementList.filter { it.lowercase().startsWith(args[1].lowercase()) }
        } else mutableListOf()
    }

}