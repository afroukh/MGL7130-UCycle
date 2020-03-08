package ca.uqam.ucycle.data

import com.google.firebase.database.Exclude

data class Category (

    @get:Exclude
    var id: String? = null,
    var name: String? = null
)