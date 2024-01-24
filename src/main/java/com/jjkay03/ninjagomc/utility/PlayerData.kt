package com.jjkay03.ninjagomc.utility

import com.google.common.cache.CacheBuilder
import com.jjkay03.ninjagomc.NinjagoMC
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import com.google.common.cache.Cache
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import java.io.IOException
import java.time.Duration
import java.util.UUID
import kotlin.random.Random

class PlayerData: Listener {

    companion object {
        const val ELEMENTS_LIST_KEY = "elements-list"
        const val HOTKEY_LIST_KEY = "hotkey-list"
        private val DEFAULT_LIST_NULL_KEYS_LIST = listOf(ELEMENTS_LIST_KEY)

        // Returns all elements a player has
        fun readElementsList(player: Player): List<String> {
            val uuid = player.uniqueId
            val playerFile = File(NinjagoMC.PLAYER_DATA_FOLDER, "$uuid.yml")

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
        updateDefaultKeys(player)
        updateDefaultListNullKeys(player, DEFAULT_LIST_NULL_KEYS_LIST)
        if(NinjagoMC.RANDOM_ELEMENT){assignRandomElement(player, NinjagoMC.RANDOM_ELEMENT_DELAY)}
    }

    // Create YAML files for players based on UUID if they don't exist
    private fun createPlayerYamlFile(player: Player) {
        val uuid = player.uniqueId
        val playerFile = File(NinjagoMC.PLAYER_DATA_FOLDER, "$uuid.yml")
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
        val playerFile = File(NinjagoMC.PLAYER_DATA_FOLDER, "$uuid.yml")

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

    // Update default keys
    private fun updateDefaultKeys(player: Player) {
        val uuid = player.uniqueId
        val playerFile = File(NinjagoMC.PLAYER_DATA_FOLDER, "$uuid.yml")

        try {
            val config = YamlConfiguration.loadConfiguration(playerFile)

            // Update keys
            val defaultKeys = listOf("display-actionbar", "display-scoreboard")

            for (key in defaultKeys) {
                if (!config.contains(key)) {
                    // Key doesn't exist, create it and set it to default value
                    when (key) {
                        "display-actionbar" -> config.set(key, true)
                        "display-scoreboard" -> config.set(key, false)
                    }
                }
            }

            // Save the updated configuration
            config.save(playerFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Update empty keys in the player's YAML file with null if they don't exist
    private fun updateDefaultNullKeys(player: Player, keys: List<String>) {
        val uuid = player.uniqueId
        val playerFile = File(NinjagoMC.PLAYER_DATA_FOLDER, "$uuid.yml")

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
        val playerFile = File(NinjagoMC.PLAYER_DATA_FOLDER, "$uuid.yml")

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

    // Assign random element
    private fun assignRandomElement(player: Player, delaySeconds: Int) {
        val ninjagoPlayer = NinjagoPlayer.get(player.uniqueId)

        // Check if the player has no elements
        if (ninjagoPlayer.elements_list.isEmpty()) {
            object : BukkitRunnable() {
                override fun run() {
                    val eligibleElements = ElementsID.values().filter {
                        it.implemented && it.type != "ESSENCES"
                    }

                    if (eligibleElements.isNotEmpty()) {
                        val randomElement = eligibleElements[Random.nextInt(eligibleElements.size)]

                        ninjagoPlayer.elements_list.add(randomElement)

                        val titleText = randomElement.label
                        val subTitleText = "§7Use this powers wisely"
                        player.sendTitle(titleText, subTitleText, 10, 40, 10)
                        player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.5f)

                        // Save the player's data
                        ninjagoPlayer.save()

                        // Print to console
                        Bukkit.getLogger().info("Random element ${randomElement.name} assigned to ${player.name}")
                    }
                }
            }.runTaskLater(NinjagoMC.instance, (20L * delaySeconds))
        }
    }

}