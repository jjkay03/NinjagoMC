package com.jjkay03.ninjagomc.elementssystem

enum class AbilitiesID(val id: Int, val label: String, val elementsID: ElementsID) {
    // FIRE
    FIRE_SPINJITZU(1, "Spinjitzu", ElementsID.FIRE),
    IGNITE(2, "Ignite", ElementsID.FIRE),
    FIRE_SHOT(3, "Fire Shot", ElementsID.FIRE),
    FIREPROOF(4, "Fireproof", ElementsID.FIRE),

    // ICE
    FROSTBITE(1, "Frostbite", ElementsID.ICE),
    ICE_SHOT(2, "Ice Shot", ElementsID.ICE),

    // LIGHTNING
    SMITE(1, "Smite", ElementsID.LIGHTNING),

    // WATER
    MERMAID(1, "Mermaid", ElementsID.WATER),
    WATER_SHOT(2, "Water Shot", ElementsID.WATER)
}