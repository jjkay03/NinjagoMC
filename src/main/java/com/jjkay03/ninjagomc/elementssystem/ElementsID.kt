package com.jjkay03.ninjagomc.elementssystem

enum class ElementsID(val id: String, val label: String, val type: String, val implemented: Boolean) {
    // Elements of creation
    FIRE("FIRE", "§c§lFIRE", "CREATION", true),
    ICE("ICE", "§f§lICE", "CREATION", false),
    LIGHTNING("LIGHTNING", "§9§lLIGHTNING", "CREATION", false),
    EARTH("EARTH", "§8§lEARTH", "CREATION", false),

    // Elements of essences
    CREATION("CREATION", "§7Creation", "ESSENCES", false),
    DARKNESS("DARKNESS", "§7Darkness", "ESSENCES", false),
    DESTRUCTION("DESTRUCTION", "§7Destruction", "ESSENCES", false),
    ENERGY("ENERGY", "§7Energy", "ESSENCES", false),
    GOLDEN_POWER("GOLDEN_POWER", "§7Golden Power", "ESSENCES", false),

    // Other elements
    AMBER("AMBER", "§7Amber", "OTHER", false),
    FORM("FORM", "§7Form", "OTHER", false),
    FUSION("FUSION", "§7Fusion", "OTHER", false),
    GRAVITY("GRAVITY", "§7Gravity", "OTHER", false),
    HEAT("HEAT", "§7Heat", "OTHER", false),
    LIGHT("LIGHT", "§7Light", "OTHER", false),
    METAL("METAL", "§7Metal", "OTHER", false),
    MIND("MIND", "§7Mind", "OTHER", false),
    NATURE("NATURE", "§7Nature", "OTHER", false),
    POISON("POISON", "§7Poison", "OTHER", false),
    SHADOW("SHADOW", "§7Shadow", "OTHER", false),
    SMOKE("SMOKE", "§7Smoke", "OTHER", false),
    SOUND("SOUND", "§7Sound", "OTHER", false),
    SPEED("SPEED", "§7Speed", "OTHER", false),
    TECHNOLOGY("TECHNOLOGY", "§7Technology", "OTHER", false),
    TIME("TIME", "§7Time", "OTHER", false),
    WATER("WATER", "§7Water", "OTHER", false),
    WIND("WIND", "§7Wind", "OTHER", false)
}
