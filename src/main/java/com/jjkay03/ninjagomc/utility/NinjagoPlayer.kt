package com.jjkay03.ninjagomc.utility

import com.jjkay03.ninjagomc.NinjagoMC
import com.jjkay03.ninjagomc.elementssystem.ElementsID
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.util.UUID

class NinjagoPlayer(val uuid : UUID, val name: String, val elements_list: MutableSet<ElementsID>, val hotkey_list: MutableList<Hotkey>) {
    companion object {
        // Load player data from yml file or cache
        fun get(uuid: UUID) : NinjagoPlayer {
            val cachedPlayer = PlayerData.playerDataCache.getIfPresent(uuid)
            if (cachedPlayer != null){
                return cachedPlayer
            }

            val playerFile = File(NinjagoMC.PLAYERDATAFOLDER, "$uuid.yml")
            val config = YamlConfiguration.loadConfiguration(playerFile)
            val hotkeys = mutableListOf<Hotkey>()
            for (i in 0..8) {
                if (config.getString("${PlayerData.HOTKEY_LIST_KEY}.$i.element") == "" || config.getString("${PlayerData.HOTKEY_LIST_KEY}.$i.element") == null ){
                    hotkeys.add(Hotkey(null, null))
                }
                else {
                    hotkeys.add(
                        Hotkey(
                            ElementsID.valueOf(config.getString("${PlayerData.HOTKEY_LIST_KEY}.$i.element", "")!!),
                            config.getInt("${PlayerData.HOTKEY_LIST_KEY}.$i.ability")
                    ))
                }
            }
            val ninjagoPlayerData = NinjagoPlayer(
                uuid,
                config.getString("last-account-name")?:"unknown",
                config.getStringList(PlayerData.ELEMENTS_LIST_KEY).map { ElementsID.valueOf(it) }.toMutableSet(),
                hotkeys
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
        // Hotkeys
        for (i in 0..8) {
            config.set("${PlayerData.HOTKEY_LIST_KEY}.$i.element", hotkey_list[i].element?.toString()?:"")
            config.set("${PlayerData.HOTKEY_LIST_KEY}.$i.ability", hotkey_list[i].ability?:-1)
        }
        config.save(playerFile)
        PlayerData.playerDataCache.put(uuid, this)
    }

    data class Hotkey(val element: ElementsID?, val ability: Int?)

}

