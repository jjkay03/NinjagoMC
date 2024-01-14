package com.jjkay03.ninjagomc.elementssystem

enum class ElementsID(val id: String, val label: String, val type: String, val implemented: Boolean) {
    // Elements of creation
    FIRE("FIRE", "§4§lFIRE", "CREATION", true),
    ICE("ICE", "§f§lICE", "CREATION", false),
    LIGHTNING("LIGHTNING", "§9§lLIGHTNING", "CREATION", false),
    EARTH("EARTH", "§8§lEARTH", "CREATION", false),

    // Elements of essences
    CREATION("CREATION", "§e§lCREATION", "ESSENCES", false),
    DARKNESS("DARKNESS", "§0§lDARKNESS", "ESSENCES", false),
    DESTRUCTION("DESTRUCTION", "§5§lDESTRUCTION", "ESSENCES", false),
    ENERGY("ENERGY", "§2§lENERGY", "ESSENCES", false),
    GOLDEN_POWER("GOLDEN_POWER", "§6§lGOLDEN POWER", "ESSENCES", false),

    // Other elements
    AMBER("AMBER", "§e§lAMBER", "OTHER", false),
    FORM("FORM", "§5§lFORM", "OTHER", false),
    //FUSION("FUSION", "§7§lFUSION", "OTHER", false),
    GRAVITY("GRAVITY", "§4§lGRAVITY", "OTHER", false),
    HEAT("HEAT", "§c§lHEAT", "OTHER", false),
    LIGHT("LIGHT", "§f§lLIGHT", "OTHER", false),
    METAL("METAL", "§8§lMETAL", "OTHER", false),
    MIND("MIND", "§7§lMIND", "OTHER", false),
    NATURE("NATURE", "§a§lNATURE", "OTHER", false),
    POISON("POISON", "§a§lPOISON", "OTHER", false),
    SHADOW("SHADOW", "§8§lSHADOW", "OTHER", false),
    SMOKE("SMOKE", "§7§lSMOKE", "OTHER", false),
    SOUND("SOUND", "§3§lSOUND", "OTHER", false),
    SPEED("SPEED", "§f§lSPEED", "OTHER", false),
    TECHNOLOGY("TECHNOLOGY", "§d§lTECHNOLOGY", "OTHER", false),
    TIME("TIME", "§c§lTIME", "OTHER", false),
    WATER("WATER", "§b§lWATER", "OTHER", false),
    WIND("WIND", "§2§lWIND", "OTHER", false)
}
