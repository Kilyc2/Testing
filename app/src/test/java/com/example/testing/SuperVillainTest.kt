package com.example.testing

import com.example.testing.VillainData.DESCRIPTION
import com.example.testing.VillainData.FIRST_NAME_ALT
import com.example.testing.VillainData.FIRST_NAME_MULTIPLE
import com.example.testing.VillainData.FULL_NAME_ALT
import com.example.testing.VillainData.FULL_NAME_MAIN
import com.example.testing.VillainData.FULL_NAME_MULTIPLE
import com.example.testing.VillainData.LAST_NAME_ALT
import com.example.testing.VillainData.LAST_NAME_MAIN
import com.example.testing.VillainData.SECRET_PLAN
import com.example.testing.VillainData.SECRET_PLAN_CIPHERED
import com.example.testing.VillainData.getCities
import com.example.testing.VillainData.getMainSuperVillain
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.quality.Strictness

class SuperVillainTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    private lateinit var sut: SuperVillain
    @Mock
    private lateinit var sidekickMock: Sidekick

    @Before
    fun setUp() {
        sut = getMainSuperVillain()
    }

    @Test
    fun fullNameIsFirstNameSpaceLastName() {
        val fullName = sut.fullName

        assertEquals(FULL_NAME_MAIN, fullName)
    }

    @Test
    fun fullNameSetsFirstNameAndLastName() {
        sut.fullName = FULL_NAME_ALT

        assertEquals(FIRST_NAME_ALT, sut.firstName)
        assertEquals(LAST_NAME_ALT, sut.lastName)
    }

    @Test
    fun fullNameMultipleSetsFirstNameAndLastName() {
        sut.fullName = FULL_NAME_MULTIPLE

        assertEquals(FIRST_NAME_MULTIPLE, sut.firstName)
        assertEquals(LAST_NAME_MAIN, sut.lastName)
    }

    @Test
    fun attackFiresWeapon() {
        val steelArms = WeaponDouble()

        sut.attack(steelArms)

        assertTrue(steelArms.isFired)
    }

    @Test
    fun attackFireFirstWeapon() {
        val weapons = listOf(WeaponDouble(), WeaponDouble(), WeaponDouble())

        sut.attack(weapons)

        assertTrue(weapons[0].isFired)
        weapons.subList(1, weapons.lastIndex).forEach {
            assertFalse(it.isFired)
        }
    }

    @Test
    fun toStringShowDescription() {
        assertEquals("$DESCRIPTION ${sut.fullName}", sut.toString())
    }

    @Test
    fun sidekickAgree() {
        sut.sidekick = AwesomeSidekick()
        sut.conspire()

        assertNotNull(sut.sidekick)
    }

    @Test
    fun sidekickDead() {
        sut.sidekick = AwfulSidekick()
        sut.conspire()

        assertNull(sut.sidekick)
    }

    @Test
    fun buildHQInWeakestCity() {
        val sidekick = AwesomeSidekick()
        sidekick.cities = getCities()
        sut.sidekick = sidekick
        sut.startWorldDominationStageOne()

        assertEquals(getCities()[0], sidekick.headQuarter)
    }

    @Test
    fun minionDoBothThings() {
        val minion = MinionDouble()

        sut.startWorldDominationStageTwo(minion)

        minion.verify()
    }

    @Test
    fun secretPlanIsListened() {
        val cipherMock: Cipher = mock()

        sut.sidekick = sidekickMock
        `when`(cipherMock.transform(secret = anyString(), key = anyString())).thenAnswer {
            val secret = it.getArgument<String>(0) as String
            return@thenAnswer "# $secret #"
        }
        sut.tellSecretPlan(SECRET_PLAN, cipherMock)

//        assertEquals(SECRET_PLAN_CIPHERED, sidekick.secretPlan)
        argumentCaptor<String>().apply {
            verify(sidekickMock).listenSecretPlan(capture())
            assertEquals(SECRET_PLAN_CIPHERED, lastValue)
        }
    }

    class WeaponDouble : MegaWeapon {
        var isFired = false
        override fun fire() {
            isFired = true
        }
    }

    class AwesomeSidekick(gadget: Gadget = DummyGadget()) : Sidekick(gadget) {
        var headQuarter: String? = null
        var cities = emptyList<String>()
        var secretPlan = ""

        override fun agree(): Boolean {
            return true
        }

        override fun getWeakCities(): List<String> {
            return cities
        }

        override fun buildHQ(city: String) {
            headQuarter = city
        }

        override fun listenSecretPlan(secretPlan: String) {
            this.secretPlan = secretPlan
        }
    }

    class AwfulSidekick(gadget: Gadget = DummyGadget()) : Sidekick(gadget) {
        override fun agree(): Boolean {
            return false
        }
    }

    class SecretCipher : Cipher {
        override fun transform(secret: String, key: String): String {
            return "# $secret #"
        }
    }

    class DummyGadget : Gadget {
        override fun doStuff() {

        }
    }

    class MinionDouble : Minion {
        var order = 1
        var doHardThingsCalled = 0
        var doFightEnemiesCalled = 0

        override fun doHardThings() {
            doHardThingsCalled = order
            order++
        }

        override fun fightEnemies() {
            doFightEnemiesCalled = order
            order++
        }

        fun verify() {
            assertEquals(1, doHardThingsCalled)
            assertEquals(2, doFightEnemiesCalled)
        }
    }
}

