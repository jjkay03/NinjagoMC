package com.jjkay03.ninjagomc

import com.jjkay03.ninjagomc.commands.NinjagoMCCommand
import com.jjkay03.ninjagomc.utility.CreatePlayerData
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

        // Register event handler
        server.pluginManager.registerEvents(CreatePlayerData(), this)

    }

    // Plugin startup logic
    override fun onDisable() {

    }
}
