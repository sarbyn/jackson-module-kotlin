package com.fasterxml.jackson.module.kotlin.test


import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert
import org.junit.Test

open class Wrapper(@JsonValue val value: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Wrapper

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int =
            value.hashCode()

    override fun toString(): String =
            value

}

class ExtendedWrapper(value: String) : Wrapper(value)

// All tests work in 2.9.6, 2.9.7, 2.9.8
class WrappersJavaTest {

    private val objectMapper = ObjectMapper()

    @Test
    fun shouldDeserializeJsonString() {
        // given
        val json = "\"foo\""

        // when
        val deserialized = objectMapper.readValue(json, Wrapper::class.java)

        // then
        Assert.assertEquals(Wrapper("foo"), deserialized)
    }

    @Test
    fun shouldSerializeJsonString() {
        // given
        val wrapperObject = Wrapper("foo")

        // when
        val serialized = objectMapper.writeValueAsString(wrapperObject)

        // then
        Assert.assertEquals("\"foo\"", serialized)
    }

    @Test
    fun shouldDeserializeJsonString_Extended() {
        // given
        val json = "\"foo\""

        // when
        val deserialized = objectMapper.readValue(json, ExtendedWrapper::class.java)

        // then
        Assert.assertEquals(ExtendedWrapper("foo"), deserialized)
    }

    @Test
    fun shouldSerializeJsonString_Extended() {
        // given
        val wrapperObject = ExtendedWrapper("foo")

        // when
        val serialized = objectMapper.writeValueAsString(wrapperObject)

        // then
        Assert.assertEquals("\"foo\"", serialized)
    }

}