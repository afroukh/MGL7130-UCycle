package ca.uqam.ucycle.data

import com.google.firebase.database.Exclude

data class City(
    @get:Exclude
    var id: String? = null,
    var name: String? = null
)