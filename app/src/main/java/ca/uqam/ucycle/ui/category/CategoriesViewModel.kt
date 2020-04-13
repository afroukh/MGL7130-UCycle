package ca.uqam.ucycle.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.uqam.ucycle.data.CategoryRepository
import ca.uqam.ucycle.models.Category
import ca.uqam.ucycle.models.NODE_CATEGORIES
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class CategoriesViewModel : ViewModel() {

    private  val _categories = MutableLiveData<List<Category>>()
    var categories: LiveData<List<Category>> = _categories

    private val _result = MutableLiveData<Exception?>()
    var result: LiveData<Exception?> = _result

    var categoryRepository = CategoryRepository()

    fun addCategory(category: Category) {

        categoryRepository.addCategory(category)
        result = categoryRepository.result

    }

    fun fetchCategories() {
        categoryRepository.fetchCategories()
        categories = categoryRepository.categories
    }
}