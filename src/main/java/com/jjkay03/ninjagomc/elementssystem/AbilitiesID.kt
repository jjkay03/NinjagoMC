package com.jjkay03.ninjagomc.elementssystem

enum class AbilitiesID(val id: Int, val label: String, val elementsID: ElementsID) {
    // FIRE
    IGNITE(1, "Ignite", ElementsID.FIRE),
    FIRE_SHOT(2, "Fire Shot", ElementsID.FIRE),

    // ICE
    FROSTBITE(1, "Frostbite", ElementsID.ICE)
}