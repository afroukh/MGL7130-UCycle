package ca.uqam.ucycle.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.uqam.ucycle.data.NODE_CATEGORIES
import ca.uqam.ucycle.data.Product
import ca.uqam.ucycle.data.NODE_PRODUCTS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class ProductsViewModel : ViewModel() {

    private val dbProducts = FirebaseDatabase.getInstance().getReference(NODE_PRODUCTS)

    private  val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addProduct(product: Product, categoryId: String) {

        val dbProducts = FirebaseDatabase.getInstance().getReference(NODE_CATEGORIES).child(categoryId).child(NODE_PRODUCTS)
        product.id = dbProducts.push().key
        dbProducts.child(product.id!!).setValue(product)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    fun fetchProducts() {
            dbProducts.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapShot: DataSnapshot) {
                if (snapShot.exists()) {
                    val products = mutableListOf<Product>()
                    for(productSnapshot in snapShot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        product?.id = productSnapshot.key
                        product?.let { products.add(it) }
                    }
                    _products.value = products
                }
            }

        })
    }

}