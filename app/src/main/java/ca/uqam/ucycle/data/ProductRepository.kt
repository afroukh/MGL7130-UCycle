package ca.uqam.ucycle.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.uqam.ucycle.models.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Exception

class ProductRepository {
    private var dbCategories = FirebaseDatabase.getInstance().getReference(NODE_CATEGORIES)

    private val storageRef: StorageReference =
        FirebaseStorage.getInstance().getReference(NODE_IMAGES)

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product


    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result


    fun addProduct(product: Product, categoryId: String, filePath: Uri) {

        val dbProducts =
            FirebaseDatabase.getInstance().getReference(NODE_CATEGORIES).child(categoryId)
                .child(NODE_PRODUCTS)


        product.id = dbProducts.push().key
        dbProducts.child(product.id!!).setValue(product)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var imageRef = storageRef.child(product.id.toString())
                    imageRef.putFile(filePath)
                        .addOnSuccessListener {
                            imageRef.downloadUrl.addOnSuccessListener { uri ->
                                dbProducts.child(product.id!!).child(Product::urlImage.name)
                                    .setValue(uri.toString())
                                    .addOnCompleteListener { updt ->
                                        if (updt.isSuccessful) {
                                            _result.value = null
                                        } else {
                                            _result.value = updt.exception
                                        }
                                    }
                            }.addOnFailureListener { exception -> _result.value = exception }
                        }.addOnFailureListener { exception -> _result.value = exception }

                        .addOnFailureListener { exception -> _result.value = exception }
                } else {
                    _result.value = it.exception
                }
            }

    }

    fun getProduct(productId: String, categoryId: String) {
        val dbProducts =
            FirebaseDatabase.getInstance().getReference(NODE_CATEGORIES).child(categoryId)
                .child(NODE_PRODUCTS).child(productId)

        dbProducts.addListenerForSingleValueEvent(object : ValueEventListener {


            override fun onDataChange(productSnapshot: DataSnapshot) {
                if (productSnapshot.exists()) {
                    val product = productSnapshot.getValue(Product::class.java)
                    product?.id = productSnapshot.key
                    product?.categoryId = categoryId

                    _product.value = product
                }
            }
            override fun onCancelled(p0: DatabaseError) {}

        })
    }

    fun fetchProducts(category: Category) {


        dbCategories = dbCategories.child(category.id!!).ref


        dbCategories.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapShot: DataSnapshot) {
                if (snapShot.exists()) {
                    val products = mutableListOf<Product>()

                    var productsRef = snapShot.child(NODE_PRODUCTS).ref
                    productsRef.addValueEventListener(object : ValueEventListener {

                        override fun onDataChange(productsSnapshot: DataSnapshot) {
                            if (productsSnapshot.exists()) {
                                for (productSnapshot in productsSnapshot.children) {
                                    val product = productSnapshot.getValue(Product::class.java)
                                    product?.id = productSnapshot.key
                                    product?.categoryId = snapShot.key
                                    product?.let { products.add(it) }
                                }
                                _products.value = products
                            }

                        }

                        override fun onCancelled(p0: DatabaseError) {}
                    })


                }

            }
        })

    }


    fun fetchAllProducts() {

        dbCategories.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapShot: DataSnapshot) {
                if (snapShot.exists()) {
                    val products = mutableListOf<Product>()
                    for (categorySnapshot in snapShot.children) {
                        var productsRef = categorySnapshot.child(NODE_PRODUCTS).ref
                        productsRef.addValueEventListener(object :
                            ValueEventListener {

                            override fun onDataChange(productsSnapshot: DataSnapshot) {
                                if (productsSnapshot.exists()) {
                                    for (productSnapshot in productsSnapshot.children) {
                                        val product = productSnapshot.getValue(Product::class.java)
                                        product?.id = productSnapshot.key
                                        product?.categoryId = categorySnapshot.key
                                        product?.let { products.add(it) }
                                    }
                                }
                            }

                            override fun onCancelled(p0: DatabaseError) {}

                        })

                    }
                    _products.value = products
                }
            }

            override fun onCancelled(error: DatabaseError) {}


        })
    }


    companion object {
        fun newInstance(): ProductRepository = ProductRepository()
    }
}