package ca.uqam.ucycle.models

import com.google.firebase.database.Exclude

data class City(
    @get:Exclude
    var id: String? = null,
    var name: String? = null
)