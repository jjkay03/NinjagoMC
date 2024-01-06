package com.jjkay03.ninjagomc.utility

import com.jjkay03.ninjagomc.NinjagoMC
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File
import java.io.IOException

class CreatePlayerData: Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        createPlayerYamlFile(player) // Create player data
        updateLastAccountNameKey(player)
    }

    // Create YAML files for players based on UUID if they don't exist
    fun createPlayerYamlFile(player: Player) {
        val uuid = player.uniqueId
        val playerFile = File(NinjagoMC.PLAYERDATAFOLDER, "$uuid.yml")
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile()
                val config = YamlConfiguration.loadConfiguration(playerFile)
                config.save(playerFile)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // Update last account name in the player's YAML file
    private fun updateLastAccountNameKey(player: Player) {
        val uuid = player.uniqueId
        val playerFile = File(NinjagoMC.PLAYERDATAFOLDER, "$uuid.yml")

        if (playerFile.exists()) {
            val config = YamlConfiguration.loadConfiguration(playerFile)
            if (!config.contains("last-account-name")) {
                config.set("last-account-name", player.name)
                try {
                    config.save(playerFile)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                val lastAccountName = config.getString("last-account-name")
                if (lastAccountName != player.name) {
                    config.set("last-account-name", player.name)
                    try {
                        config.save(playerFile)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

}