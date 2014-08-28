# Overview

Module that adds support for serialization/deserialization of [Kotlin](http://kotlinlang.org) classes and data classes.

Compatible with Jackson 2.4 and newer.

# Status

This module is not yet released, but can be built manually until the first release.

# Usage

For any Kotlin class or data class constructor annotated with [JsonCreator] annotation, the JSON property names will be infered in the constructor using Kotlin runtime type information (currently an annotation compatible with Kotlin 0.8.11 and in the future will use the full Kotlin runtime type information when available).

Without this module, a Kotlin class must have all properties with default values and any JSON property must be of type var.  By adding this module you can use immutable classes and also the primary non default Kotlin constructor.

To use, just register the Kotlin module with your ObjectMapper instance:
```kotlin
ObjectMapper().registerModule(KotlinModule())
```

A data class example:
```kotlin
data class MyStateObject [JsonCreator] (val name: String, val age: Int)

fun jsonToMyStateObject(json: String): MyStateObject {
    val mapper = ObjectMapper().registerModule(KotlinModule())
    return mapper.readValue(json, javaClass<MyStateObject>())
}
```

You can intermix non-field values in the constructor and [JsonProperty] annotation in the constructor.  Any fields not present in the constructor will be set after the constructor call and therefore must be nullable with default value.  An example of these concepts:

```kotlin
   class StateObjectWithPartialFieldsInConstructor [JsonCreator] (val name: String, JsonProperty("age") val years: Int)    {
        JsonProperty("address") var primaryAddress: String? = null
        var createdDt: DateTime by Delegates.notNull()
    }
```

Note that using Delegates.notNull() will ensure that the value is never null when read, while letting it be instantiated after the construction of the class.