package ca.uqam.ucycle.ui.product

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.uqam.ucycle.data.ProductRepository
import ca.uqam.ucycle.models.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Exception

class ProductsViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    var products: LiveData<List<Product>> = _products

    private val _product = MutableLiveData<Product>()
    var product: LiveData<Product> = _product

    private val _result = MutableLiveData<Exception?>()
    var result: LiveData<Exception?> = _result


    private var repo = ProductRepository()


    fun addProduct(product: Product, categoryId: String, filePath: Uri) {

        repo.addProduct(product, categoryId, filePath)
        result = repo.result

    }


    fun getProduct(productId: String, categoryId: String) {

        repo.getProduct(productId, categoryId)
        product = repo.product
    }

    fun fetchProducts(category: Category) {

        repo.fetchProducts(category)
        products = repo.products
    }


    fun fetchAllProducts() {
        repo.fetchAllProducts()
        products = repo.products
    }


}