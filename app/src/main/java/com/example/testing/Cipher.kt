package com.example.testing

interface Cipher {
    fun transform(secret: String, key: String): String
}