package com.jjkay03.ninjagomc.utility

import com.jjkay03.ninjagomc.NinjagoMC
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File
import java.io.IOException

class PlayerData: Listener {

    companion object {
        const val ELEMENTS_LIST_KEY = "elements-list"
        private val DEFAULT_LIST_NULL_KEYS_LIST = listOf(ELEMENTS_LIST_KEY)
        private val DEFAULT_NULL_KEYS_LIST = listOf("exemple-key", "exemple-key")
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        createPlayerYamlFile(player) // Create player data
        updateLastAccountNameKey(player)
        updateDefaultListNullKeys(player, DEFAULT_LIST_NULL_KEYS_LIST)
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

    // Update empty keys in the player's YAML file with null if they don't exist
    private fun updateDefaultNullKeys(player: Player, keys: List<String>) {
        val uuid = player.uniqueId
        val playerFile = File(NinjagoMC.PLAYERDATAFOLDER, "$uuid.yml")

        if (playerFile.exists()) {
            val config = YamlConfiguration.loadConfiguration(playerFile)
            var changesMade = false

            for (key in keys) {
                // Check if the key doesn't exist
                if (!config.contains(key)) {
                    // Add the key with a null value
                    config.set(key, "")
                    changesMade = true
                }
            }

            // Save the changes back to the file
            if (changesMade) {
                try {
                    config.save(playerFile)
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    // Update empty list of keys in the player's YAML file with null if they don't exist
    private fun updateDefaultListNullKeys(player: Player, keys: List<String>) {
        val uuid = player.uniqueId
        val playerFile = File(NinjagoMC.PLAYERDATAFOLDER, "$uuid.yml")

        if (playerFile.exists()) {
            val config = YamlConfiguration.loadConfiguration(playerFile)
            var changesMade = false

            for (key in keys) {
                // Check if the key doesn't exist
                if (!config.contains(key)) {
                    config.set(key, emptyList<Any>())
                    changesMade = true
                }
            }

            // Save the changes back to the file
            if (changesMade) {
                try {
                    config.save(playerFile)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

}