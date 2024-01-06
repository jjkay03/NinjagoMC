package com.jjkay03.ninjagomc

import com.jjkay03.ninjagomc.commands.NinjagoMCCommand
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
        console.sendMessage("§a┌-----------------------------┐")
        console.sendMessage("§a|          NINJAGOMC          |")
        console.sendMessage("§a|         IS RUNNING!         |")
        console.sendMessage("§a└-----------------------------┘")
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

    }

    // Plugin startup logic
    override fun onDisable() {

    }
}
