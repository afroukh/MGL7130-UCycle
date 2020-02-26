package ca.uqam.ucycle.repositories

import ca.uqam.ucycle.models.Product

class ProductRepository {

    fun getAllProduct(): List<Product> {
       return listOf(
            Product("Persian Cat", "https://i.picsum.photos/id/867/200/300.jpg"),
            Product("German Fisher", "https://i.picsum.photos/id/868/200/400.jpg"),
            Product("Old Desk","https://i.picsum.photos/id/869/200/600.jpg"),
            Product("Dryer", "https://i.picsum.photos/id/870/200/300.jpg"),
            Product("Winter Tires", "https://i.picsum.photos/id/871/200/400.jpg"),
            Product("Vintage Hour", "https://i.picsum.photos/id/872/200/300.jpg"),
            Product("Baseball Cards", "https://i.picsum.photos/id/873/200/300.jpg")
        )

    }

}