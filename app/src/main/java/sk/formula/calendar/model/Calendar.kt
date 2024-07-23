package sk.formula.calendar.model

import kotlinx.serialization.Serializable

@Serializable
data class Calendar(
    val year: Int,
    val grandPrixes: Map<String, GrandPrix>
)

@Serializable
data class GrandPrix(
    val name: String,
    val cancelled: Boolean,
    val location: Location
)

@Serializable
data class Location(
    val circuit: String,
    val city: String,
    val country: Country
)

@Serializable
data class Country(
    val name: String,
    val abbreviation: String
)