package com.example.testing

data class SuperVillain(
    var firstName: String,
    var lastName: String = "",
    var sidekick: Sidekick? = null
) {

    var fullName: String
        get() = "$firstName $lastName"
        set(value) {
            val fullName = value.split(" ")
            when (fullName.size) {
                0 -> {
                    firstName = ""
                    lastName = ""
                }
                1 -> {
                    firstName = fullName[0]
                    lastName = ""
                }
                2 -> {
                    firstName = fullName[0]
                    lastName = fullName[1]
                }
                else -> {
                    lastName = fullName[fullName.lastIndex]
                    firstName = value.replace(lastName, "").trim()
                }
            }
        }

    fun attack(weapon: MegaWeapon) {
        weapon.fire()
    }

    fun attack(weapons: List<MegaWeapon>) {
        weapons[0].fire()
    }

    override fun toString(): String {
        return "Mi canario favorito es $fullName"
    }

    fun conspire() {
        if (sidekick?.agree() == false) {
            sidekick = null
        }
    }

    fun startWorldDominationStageOne() {
        sidekick?.run {
            val cities = getWeakCities()
            buildHQ(cities[0])
        }
    }

    fun startWorldDominationStageTwo(minion: Minion) {
        minion.doHardThings()
        minion.fightEnemies()
    }

    fun tellSecretPlan(secretPlan: String, cipher: Cipher) {
        sidekick?.run {
            val secretPlanCiphered = cipher.transform(secretPlan, "SECRET-KEY")
            listenSecretPlan(secretPlanCiphered)
        }
    }
}