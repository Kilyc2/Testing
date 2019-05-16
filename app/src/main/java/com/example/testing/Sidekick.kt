package com.example.testing

open class Sidekick(val gadget: Gadget) {

    open fun agree(): Boolean {
        return false
    }

    open fun getWeakCities(): List<String> = listOf()

    open fun buildHQ(city: String) {

    }

    open fun listenSecretPlan(secretPlan: String) {

    }
}