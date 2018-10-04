package io.secrethitler

class GameCards {
    enum class VoteCard (val isYes: Boolean) {
        JA(true),
        NEIN(false);
    }

    enum class RoleCard (val cardName: String, val isLiberal: Boolean, val isHitler: Boolean = false) {
        LIBERAL1("Lib1", true),
        LIBERAL2("Lib2", true),
        LIBERAL3("Lib3", true),
        LIBERAL4("Lib4", true),
        LIBERAL5("Lib5", true),
        LIBERAL6("Lib6", true),

        FASCIST1("Fas1", false),
        FASCIST2("Fas2", false),
        FASCIST3("Fas3", false),
        FASCIST4("Fas4", false),

        HITLER("FasHit", false, true);

        companion object {
            fun fromName (cardName: String): RoleCard? {
                return RoleCard.values().singleOrNull { card -> card.cardName == cardName }
            }

            fun fromRole (role: String): RoleCard {
                return when (role.toLowerCase()) {
                    "lib" -> LIBERAL1
                    "fas" -> FASCIST1
                    "hit" -> HITLER
                    else -> throw error("Role must be lib/fas/hit, was $role.")
                }
            }
        }
    }

    enum class PolicyCard (val isLiberal: Boolean) {
        LIBERAL(true),
        FASCIST(false);

        companion object {
            fun fromBoolean (isLiberal: Boolean): PolicyCard {
                return if (isLiberal) LIBERAL else FASCIST
            }
        }
    }
}