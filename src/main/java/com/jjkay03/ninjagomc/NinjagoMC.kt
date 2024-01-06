package com.jjkay03.ninjagomc

import org.bukkit.Bukkit
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger

class NinjagoMC : JavaPlugin() {
    companion object {
        lateinit var instance: NinjagoMC // Define a companion object to hold the instance
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
        val playerDataFolder = File(dataFolder, "player_data"); if (!playerDataFolder.exists()) {playerDataFolder.mkdirs()}

        // Get config settings
        val prefix = config.getString("prefix") ?: ""

    }

    // Plugin startup logic
    override fun onDisable() {

    }
}
