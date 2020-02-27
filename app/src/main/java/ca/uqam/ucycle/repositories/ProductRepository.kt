package ca.uqam.ucycle.repositories
import ca.uqam.ucycle.models.Product

class ProductRepository {


    companion object {

        fun newInstance(): ProductRepository = ProductRepository()

        val PRODUCTS = listOf(
            Product("Persian Cat", "https://i.picsum.photos/id/867/500/300.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("German Fisher", "https://i.picsum.photos/id/868/500/400.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("Old Desk","https://i.picsum.photos/id/869/500/600.jpg",description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("Dryer", "https://i.picsum.photos/id/870/500/300.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("Winter Tires", "https://i.picsum.photos/id/871/500/400.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("Vintage Hour", "https://i.picsum.photos/id/872/500/300.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("Baseball Cards", "https://i.picsum.photos/id/873/500/300.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s")
        )
    }

}