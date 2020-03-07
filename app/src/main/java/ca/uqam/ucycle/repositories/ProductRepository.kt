package ca.uqam.ucycle.repositories
import ca.uqam.ucycle.models.Category
import ca.uqam.ucycle.models.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductRepository {


    companion object {

        private const val NODE_CATEGORIES = "categories"
        private val dbCategories = FirebaseDatabase.getInstance().getReference(NODE_CATEGORIES)

        val PRODUCTS = listOf(
            Product("Persian Cat", "https://i.picsum.photos/id/867/500/300.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s" +
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s" +
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("German Fisher", "https://i.picsum.photos/id/868/500/400.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("Old Desk","https://i.picsum.photos/id/869/500/600.jpg",description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("Dryer", "https://i.picsum.photos/id/870/500/300.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("Winter Tires", "https://i.picsum.photos/id/871/500/400.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("Vintage Hour", "https://i.picsum.photos/id/872/500/300.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"),
            Product("Baseball Cards", "https://i.picsum.photos/id/873/500/300.jpg", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s")
        )


        fun addCategory(category: Category): String? {

            var message = ""

            category.id = dbCategories.push().key
            dbCategories.child(category.id!!).setValue(category)
                .addOnCompleteListener {
                    message = if (it.isSuccessful) {
                        "Added Successfully"
                    } else {
                        "Error"
                    }
                }
            return message

        }

        fun fetchCategories(): MutableList<Category>  {
            var categories = mutableListOf<Category>()
            dbCategories.addListenerForSingleValueEvent( object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                  if (snapshot.exists()) {
                      for(categorySnapshot in snapshot.children) {
                         val category = categorySnapshot.getValue(Category::class.java)
                          category?.id = categorySnapshot.key
                          category?.let { categories.add(it) }
                      }


                  }
                }

            })
            return categories
        }



    }

}