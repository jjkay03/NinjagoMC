package com.jjkay03.ninjagomc.utility

import com.google.common.cache.CacheBuilder
import com.jjkay03.ninjagomc.NinjagoMC
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import com.google.common.cache.Cache
import java.io.File
import java.io.IOException
import java.time.Duration
import java.util.UUID

class PlayerData: Listener {

    companion object {
        const val ELEMENTS_LIST_KEY = "elements-list"
        const val HOTKEY_LIST_KEY = "hotkey-list"
        private val DEFAULT_LIST_NULL_KEYS_LIST = listOf(ELEMENTS_LIST_KEY)
        private val DEFAULT_NULL_KEYS_LIST = listOf("exemple-key", "exemple-key")

        // Returns all elements a player has
        fun readElementsList(player: Player): List<String> {
            val uuid = player.uniqueId
            val playerFile = File(NinjagoMC.PLAYERDATAFOLDER, "$uuid.yml")

            if (playerFile.exists()) {
                val config = YamlConfiguration.loadConfiguration(playerFile)
                return config.getStringList(ELEMENTS_LIST_KEY)
            }

            return emptyList()
        }

        val playerDataCache : Cache<UUID, NinjagoPlayer> = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(30))
            .concurrencyLevel(8)
            .initialCapacity(10)
            .build<UUID, NinjagoPlayer>()
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        createPlayerYamlFile(player) // Create player data
        updateLastAccountNameKey(player)
        updateDefaultListNullKeys(player, DEFAULT_LIST_NULL_KEYS_LIST)
        createDefaultHotkeyList(player) // Create default hotkey list
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

    // Create default hotkey list for the player on join
    private fun createDefaultHotkeyList(player: Player) {
        val uuid = player.uniqueId
        val playerFile = File(NinjagoMC.PLAYERDATAFOLDER, "$uuid.yml")

        if (playerFile.exists()) {
            val config = YamlConfiguration.loadConfiguration(playerFile)
            val defaultHotkeyList = List(9) { mapOf("element" to null, "ability" to null) }

            // Check if the hotkey list doesn't exist or is empty
            if (!config.contains(HOTKEY_LIST_KEY) || (config.getList(HOTKEY_LIST_KEY) as? List<*>).isNullOrEmpty()) {
                config.set(HOTKEY_LIST_KEY, defaultHotkeyList)
                try {
                    config.save(playerFile)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

}