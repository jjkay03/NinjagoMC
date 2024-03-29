package com.jjkay03.ninjagomc.elementssystem

enum class AbilitiesID(val id: Int, val label: String, val elementsID: ElementsID) {

    // FIRE
    FIRE_SPINJITZU(1, "Spinjitzu", ElementsID.FIRE),
    IGNITE(2, "Ignite", ElementsID.FIRE),
    FIRE_SHOT(3, "Fire Shot", ElementsID.FIRE),
    FIREPROOF(4, "Fireproof", ElementsID.FIRE),
    FIREBALL(5, "Fireball", ElementsID.FIRE),
    // TODO: BURN

    // ICE
    ICE_SPINJITZU(1, "Spinjitzu", ElementsID.ICE),
    FROSTBITE(2, "Frostbite", ElementsID.ICE),
    ICE_SHOT(3, "Ice Shot", ElementsID.ICE),

    // LIGHTNING
    LIGHTNING_SPINJITZU(1, "Spinjitzu", ElementsID.LIGHTNING),
    SMITE(2, "Smite", ElementsID.LIGHTNING),
    // TODO: THUNDER
    // TODO: THUNDER STORM
    // TODO: STORM

    // EARTH
    EARTH_SPINJITZU(1, "Spinjitzu", ElementsID.EARTH),
    // TODO: EARTH SHAKE

    // ENERGY
    ENERGY_SPINJITZU(1, "Spinjitzu", ElementsID.ENERGY),
    ENERGY_SHOT(2, "Energy Shot", ElementsID.ENERGY),
    ENERGY_CHARGE(3, "Energy Charge", ElementsID.ENERGY),

    // WATER
    MERMAID(1, "Mermaid", ElementsID.WATER),
    WATER_SHOT(2, "Water Shot", ElementsID.WATER)
    // TODO: WATERSPOUT

}