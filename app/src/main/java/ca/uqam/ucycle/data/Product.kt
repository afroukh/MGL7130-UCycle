package ca.uqam.ucycle.data

import com.google.firebase.database.Exclude

data class Product(

    @get:Exclude
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var localisation: String? = null,
    var urlImage: String? = null,
    var categoryId: String? = null
)