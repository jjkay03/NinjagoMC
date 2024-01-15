package com.jjkay03.ninjagomc.utility

import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.util.UUID

class NinjagoPlayer(val uuid : UUID, val name: String, val elements_list: MutableSet<ElementsID>) {
    companion object {
        // Load player data from yml file or cache
        fun get(uuid: UUID) : NinjagoPlayer {
            val cachedPlayer = PlayerData.playerDataCache.getIfPresent(uuid)
            if (cachedPlayer != null){
                return cachedPlayer
            }

            val playerFile = File(NinjagoMC.PLAYERDATAFOLDER, "$uuid.yml")
            val config = YamlConfiguration.loadConfiguration(playerFile)
            val ninjagoPlayerData = NinjagoPlayer(
                uuid,
                config.getString("last-account-name")?:"unknown",
                config.getStringList(PlayerData.ELEMENTS_LIST_KEY).map { ElementsID.valueOf(it) }.toMutableSet()
            )

            PlayerData.playerDataCache.put(uuid, ninjagoPlayerData)

            return ninjagoPlayerData
        }
    }

    // Save player data to yml and cache
    fun save() {
        val playerFile = File(NinjagoMC.PLAYERDATAFOLDER, "$uuid.yml")
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        val config = YamlConfiguration.loadConfiguration(playerFile)
        config.set("last-account-name", name)
        config.set(PlayerData.ELEMENTS_LIST_KEY, elements_list.map { it.toString() })
        config.save(playerFile)
        PlayerData.playerDataCache.put(uuid, this)
    }

}