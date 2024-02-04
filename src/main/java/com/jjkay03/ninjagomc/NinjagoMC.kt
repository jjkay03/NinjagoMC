package com.jjkay03.ninjagomc

import com.jjkay03.ninjagomc.commands.*
import com.jjkay03.ninjagomc.elementssystem.elements.*
import com.jjkay03.ninjagomc.utility.HotkeyDisplay
import com.jjkay03.ninjagomc.utility.PlayerData
import org.bukkit.Bukkit
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class NinjagoMC : JavaPlugin() {
    companion object {
        lateinit var instance: NinjagoMC
        lateinit var PLAYER_DATA_FOLDER: File
        var PREFIX: String = "[NinjagoMC]"
        var RANDOM_ELEMENT: Boolean = false
        var RANDOM_ELEMENT_DELAY: Int = 0
    }

    // Plugin startup logic
    override fun onEnable() {
        instance = this
        val console: ConsoleCommandSender = Bukkit.getConsoleSender()

        // Plugin welcome message
        console.sendMessage("")
        console.sendMessage("§a      __ _        _                             ___          ")
        console.sendMessage("§a   /\\ \\ (_)_ __  (_) __ _  __ _  ___   /\\/\\    / __\\    ")
        console.sendMessage("§a  /  \\/ / | '_ \\ | |/ _` |/ _` |/ _ \\ /    \\  / /        ")
        console.sendMessage("§a / /\\  /| | | | || | (_| | (_| | (_) / /\\/\\ \\/ /___      ")
        console.sendMessage("§a \\_\\ \\/ |_|_| |_|/ |\\__,_|\\__, |\\___/\\/    \\/\\____/ ")
        console.sendMessage("§a               |__/       |___/                              ")
        console.sendMessage("")

        // Display plugin version
        logger.info("Plugin version: ${description.version}")

        // Config stuff
        saveDefaultConfig() // Save the default configuration if it doesn't exist
        reloadConfig() // Reload the configuration

        // Create default folders
        PLAYER_DATA_FOLDER = File(dataFolder, "player_data"); if (!PLAYER_DATA_FOLDER.exists()) {PLAYER_DATA_FOLDER.mkdirs()}

        // Get config settings
        PREFIX = config.getString("prefix") ?: ""
        RANDOM_ELEMENT = config.getBoolean("random-element")
        RANDOM_ELEMENT_DELAY = config.getInt("random-element-delay")

        // Get Commands
        getCommand("ninjagomc")?.setExecutor(NinjagoMCCommand())
        getCommand("elements")?.setExecutor(ElementsCommand())
        getCommand("elementslist")?.setExecutor(ElementsListCommand())
        getCommand("setelements")?.setExecutor(SetElementsCommand())
        getCommand("setelements")?.tabCompleter = SetElementsCommand() // Tab completer
        getCommand("ninjagomcwiki")?.setExecutor(NinjagoMCWikiCommand())
        getCommand("bind")?.setExecutor(BindCommand())
        getCommand("bind")?.tabCompleter = BindCommand() // Tab completer
        getCommand("ninjagomcdisplay")?.setExecutor(NinjagoMCDisplay())
        getCommand("ninjagomcdisplay")?.tabCompleter = NinjagoMCDisplay() // Tab completer

        // Register event handler
        server.pluginManager.registerEvents(HotkeyDisplay(), this)
        server.pluginManager.registerEvents(PlayerData(), this)
        // Elements
        server.pluginManager.registerEvents(EL_Fire(), this) // FIRE
        server.pluginManager.registerEvents(EL_Ice(), this) // ICE
        server.pluginManager.registerEvents(EL_Lightning(), this) // LIGHTNING
        server.pluginManager.registerEvents(EL_Earth(), this) // EARTH
        server.pluginManager.registerEvents(EL_Energy(), this) // ENERGY
        server.pluginManager.registerEvents(EL_Water(), this) // WATER

    }

    // Plugin startup logic
    override fun onDisable() {

    }
}
