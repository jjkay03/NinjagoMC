package com.jjkay03.ninjagomc

import com.jjkay03.ninjagomc.commands.ElementsCommand
import com.jjkay03.ninjagomc.commands.ElementsListCommand
import com.jjkay03.ninjagomc.commands.NinjagoMCCommand
import com.jjkay03.ninjagomc.commands.SetElementsCommand
import com.jjkay03.ninjagomc.elementssystem.elements.EL_Fire
import com.jjkay03.ninjagomc.utility.PlayerData
import org.bukkit.Bukkit
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class NinjagoMC : JavaPlugin() {
    companion object {
        lateinit var instance: NinjagoMC; private set // Define a companion object to hold the instance
        lateinit var PLAYERDATAFOLDER: File; private set
        var PREFIX: String = "[NinjagoMC]"; private set
    }

    // Plugin startup logic
    override fun onEnable() {
        instance = this
        val console: ConsoleCommandSender = Bukkit.getConsoleSender()

        // Plugin welcome message
        console.sendMessage("")
        console.sendMessage("§a      __ _        _                             ___")
        console.sendMessage("§a   /\\ \\ (_)_ __  (_) __ _  __ _  ___   /\\/\\    / __\\")
        console.sendMessage("§a  /  \\/ / | '_ \\ | |/ _` |/ _` |/ _ \\ /    \\  / /")
        console.sendMessage("§a / /\\  /| | | | || | (_| | (_| | (_) / /\\/\\ \\/ /___")
        console.sendMessage("§a \\_\\ \\/ |_|_| |_|/ |\\__,_|\\__, |\\___/\\/    \\/\\____/")
        console.sendMessage("§a               |__/       |___/")
        console.sendMessage("")

        // Display plugin version
        logger.info("Plugin version: ${description.version}")

        // Config stuff
        saveDefaultConfig() // Save the default configuration if it doesn't exist
        reloadConfig() // Reload the configuration

        // Create default folders
        PLAYERDATAFOLDER = File(dataFolder, "player_data"); if (!PLAYERDATAFOLDER.exists()) {PLAYERDATAFOLDER.mkdirs()}

        // Get config settings
        PREFIX = config.getString("prefix") ?: ""

        // Get Commands
        getCommand("ninjagomc")?.setExecutor(NinjagoMCCommand())
        getCommand("elements")?.setExecutor(ElementsCommand())
        getCommand("elementslist")?.setExecutor(ElementsListCommand())
        getCommand("setelements")?.setExecutor(SetElementsCommand())
        getCommand("setelements")?.tabCompleter = SetElementsCommand() // Tab completer

        // Register event handler
        server.pluginManager.registerEvents(PlayerData(), this)
        server.pluginManager.registerEvents(EL_Fire(), this)

    }

    // Plugin startup logic
    override fun onDisable() {

    }
}
