package com.jjkay03.ninjagomc.elementssystem

enum class ElementsID(val id: String, val type: String, val implemented: Boolean) {
    // Elements of creation
    FIRE("FIRE", "CREATION", false),
    ICE("ICE", "CREATION", false),
    LIGHTNING("LIGHTNING", "CREATION", false),
    EARTH("EARTH", "CREATION", false),

    // Elements of essences
    CREATION("CREATION", "ESSENCES", false),
    DARKNESS("DARKNESS", "ESSENCES", false),
    DESTRUCTION("DESTRUCTION", "ESSENCES", false),
    ENERGY("ENERGY", "ESSENCES", false),
    GOLDEN_POWER("GOLDEN_POWER", "ESSENCES", false),

    // Other elements
    AMBER("AMBER", "OTHER", false),
    FORM("FORM", "OTHER", false),
    FUSION("FUSION", "OTHER", false),
    GRAVITY("GRAVITY", "OTHER", false),
    HEAT("HEAT", "OTHER", false),
    LIGHT("LIGHT", "OTHER", false),
    METAL("METAL", "OTHER", false),
    MIND("MIND", "OTHER", false),
    NATURE("NATURE", "OTHER", false),
    POISON("POISON", "OTHER", false),
    SHADOW("SHADOW", "OTHER", false),
    SMOKE("SMOKE", "OTHER", false),
    SOUND("SOUND", "OTHER", false),
    SPEED("SPEED", "OTHER", false),
    TECHNOLOGY("TECHNOLOGY", "OTHER", false),
    TIME("TIME", "OTHER", false),
    WATER("WATER", "OTHER", false),
    WIND("WIND", "OTHER", false)


}
