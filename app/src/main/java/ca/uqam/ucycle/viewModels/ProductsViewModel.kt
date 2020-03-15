package ca.uqam.ucycle.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.uqam.ucycle.data.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.UploadTask.*
import java.lang.Exception

class ProductsViewModel : ViewModel() {

    private val dbProducts = FirebaseDatabase.getInstance().getReference(NODE_PRODUCTS)
    private var dbCategories = FirebaseDatabase.getInstance().getReference(NODE_CATEGORIES)


    private val storageRef: StorageReference =
        FirebaseStorage.getInstance().getReference(NODE_IMAGES)

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

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


    fun fetchProducts(categoryId: String) {

        if (categoryId != "All") {
            dbCategories = dbCategories.child(categoryId).ref


            dbCategories.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(snapShot: DataSnapshot) {
                    if (snapShot.exists()) {
                        val products = mutableListOf<Product>()

                        var productsRef = snapShot.child(NODE_PRODUCTS).ref
                        productsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onDataChange(productsSnapshot: DataSnapshot) {
                                if (productsSnapshot.exists()) {
                                    for (productSnapshot in productsSnapshot.children) {
                                        val product = productSnapshot.getValue(Product::class.java)
                                        product?.id = productSnapshot.key
                                        product?.categoryId = snapShot.key
                                        product?.let { products.add(it) }
//                                        products.add(Product(id = productSnapshot.key,title = product?.title, description = product?.description,
//                                            categoryId = product?.categoryId, localisation = product?.localisation, urlImage = product?.urlImage))
                                    }
                                    _products.value = products
                                }

                            }


                        })




                    }

                }})



        }

else {
            dbCategories.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(snapShot: DataSnapshot) {
                    if (snapShot.exists()) {
                        val products = mutableListOf<Product>()
                        for (categorySnapshot in snapShot.children) {
                            var productsRef = categorySnapshot.child(NODE_PRODUCTS).ref
                            productsRef.addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onCancelled(p0: DatabaseError) {
                                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                }

                                override fun onDataChange(productsSnapshot: DataSnapshot) {
                                    if (productsSnapshot.exists()) {
                                        for(productSnapshot in productsSnapshot.children) {
                                            val product = productSnapshot.getValue(Product::class.java)
                                            product?.id = productSnapshot.key
                                            product?.categoryId = categorySnapshot.key
                                            product?.let { products.add(it)}
//                                        products.add(Product(id = productSnapshot.key,title = product?.title, description = product?.description,
//                                            categoryId = product?.categoryId, localisation = product?.localisation, urlImage = product?.urlImage))
                                        }
                                    }

                                }


                            })

                        }
                        _products.value = products
                    }
                }

            })
        }



    }



}