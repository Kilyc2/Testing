package com.example.testing

object VillainData {
    const val FIRST_NAME_MAIN = "Aristides"
    const val LAST_NAME_MAIN = "Guimera"
    const val FULL_NAME_MAIN = "Aristides Guimera"

    const val FIRST_NAME_MULTIPLE = "Aristides Aris"
    const val FULL_NAME_MULTIPLE = "Aristides Aris Guimera"

    const val FIRST_NAME_ALT = "Ruyman"
    const val LAST_NAME_ALT = "Castro"
    const val FULL_NAME_ALT = "Ruyman Castro"

    const val DESCRIPTION = "Mi canario favorito es"

    fun getMainSuperVillain(): SuperVillain =
        SuperVillain(FIRST_NAME_MAIN, LAST_NAME_MAIN)

    private const val CITY_ONE = "Amsterdam"
    private const val CITY_TWO = "Paris"
    private const val CITY_THREE = "Milan"

    fun getCities() = listOf(CITY_ONE, CITY_TWO, CITY_THREE)

    const val SECRET_PLAN = "Vamos a conquistar el mundo"
    const val SECRET_PLAN_CIPHERED = "# Vamos a conquistar el mundo #"
}