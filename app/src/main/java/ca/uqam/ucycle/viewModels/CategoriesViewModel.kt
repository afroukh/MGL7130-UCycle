package ca.uqam.ucycle.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.uqam.ucycle.data.Category
import ca.uqam.ucycle.data.NODE_CATEGORIES
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class CategoriesViewModel : ViewModel() {

    private val dbCategories = FirebaseDatabase.getInstance().getReference(NODE_CATEGORIES)

    private  val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addCategory(category: Category) {

        val dbCategories = FirebaseDatabase.getInstance().getReference(NODE_CATEGORIES)
        category.id = dbCategories.push().key
            dbCategories.child(category.id!!).setValue(category)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    fun fetchCategories() {
        dbCategories.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapShot: DataSnapshot) {
                if (snapShot.exists()) {
                    val categories = mutableListOf<Category>()
                    for(categorySnapshot in snapShot.children) {
                        val category = categorySnapshot.getValue(Category::class.java)
                        category?.id = categorySnapshot.key
                        category?.let { categories.add(it) }
                    }
                    _categories.value = categories
                }
            }

        })
    }
}