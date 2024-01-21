package com.jjkay03.ninjagomc.elementssystem

enum class AbilitiesID(val id: Int, val label: String, val elementsID: ElementsID) {
    // FIRE
    IGNITE(1, "Ignite", ElementsID.FIRE),
    FIRE_SHOT(2, "Fire Shot", ElementsID.FIRE),
    FIREPROOF(3, "Fireproof", ElementsID.FIRE),

    // ICE
    FROSTBITE(1, "Frostbite", ElementsID.ICE),
    ICE_SHOT(2, "Ice Shot", ElementsID.ICE),

    // LIGHTNING
    SMITE(1, "Smite", ElementsID.LIGHTNING)
}