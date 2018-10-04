package io.secrethitler

class GameReplay (val players: List<String>, val elections: ArrayList<ElectionData>, val gameChat: ArrayList<String>) {
    data class PolicyHand (
        val first: GameCards.PolicyCard,
        val second: GameCards.PolicyCard,
        val third: GameCards.PolicyCard?
    ) {
        fun getCount(): Int {
            return if (third == null) 2 else 3
        }

        fun getLiberal(): Int {
            var num = 0
            if (first.isLiberal) num++
            if (second.isLiberal) num++
            if (third != null && third.isLiberal) num++
            return num
        }
    }

    class ElectionData (val president: Int, val chancellor: Int, val votes: List<Boolean?>) {
        private var presHand: PolicyHand? = null
        fun getPresHand() = presHand
        fun setPresHand(hand: PolicyHand) {
            if (votes.count { v -> v == true } * 2 <= votes.count { v -> v != null }) {
                throw error("President hand cannot be assigned on a failed election.")
            }
            if (hand.third == null) throw error("President hands must have three cards.")
            if (presHand == null) {
                presHand = hand
            } else {
                throw error("President hand was already assigned.")
            }
        }

        private var chancHand: PolicyHand? = null
        fun getChancHand() = chancHand
        fun setChancHand(hand: PolicyHand) {
            if (presHand == null) throw error("President hand must be assigned first.")
            if (hand.third != null) throw error("Chancellor hands must have two cards.")
            if (chancHand == null) {
                chancHand = hand
            } else {
                throw error("Chancellor hand was already assigned.")
            }
        }

        private var playedPolicy: GameCards.PolicyCard? = null
        fun getPlayedPolicy() = playedPolicy
        fun setPlayedPolicy(policy: GameCards.PolicyCard) {
            if (chancHand == null && votes.count { v -> v == true } * 2 > votes.count { v -> v != null }) {
                throw error("Chancellor hand must be assigned first on a successful election.")
            }
            if (playedPolicy != null) throw error("Played policy was already assigned.")
            playedPolicy = policy
        }

        private var usedPower: String? = null
        fun usedPower() = usedPower

        private var usedPowerTarget: Int = -1
        fun usedPowerTarget() = usedPowerTarget

        private var deckPeek: PolicyHand? = null
        fun getDeckPeek() = deckPeek

        fun setUsedPower(powerName: String, powerTarget: Int?, powerHand: PolicyHand?) {
            when(powerName) {
                "investigate" -> {
                    if (powerTarget == null) throw error("Investigations need a target.")
                    if (powerHand != null) throw error("Investigations do not have a card hand.")
                }
                "deckpeek" -> {

                }
            }
        }
    }
}