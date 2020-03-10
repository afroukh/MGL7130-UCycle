package ca.uqam.ucycle.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ca.uqam.ucycle.R
import ca.uqam.ucycle.data.Category
import ca.uqam.ucycle.viewModels.CategoriesViewModel
import com.google.android.material.button.MaterialButton


class PostProductFragment : Fragment() {

    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var imageProduct: ImageView
    private lateinit var btnLocalisation : MaterialButton
    private lateinit var filePath: Uri
    private var categoriesHashMap :MutableMap<String, String> = mutableMapOf()
    private lateinit var categoryId: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)

        var rootView = inflater.inflate(R.layout.fragment_post_product, container, false)
        imageProduct = rootView.findViewById(R.id.post_product_image)
        btnLocalisation = rootView.findViewById(R.id.btn_localisation)


        imageProduct.setOnClickListener {
            showFileChooser()
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ArrayAdapter<String>(activity,  android.R.layout.simple_list_item_1)

        if (categoriesViewModel.categories.value == null) {
            categoriesViewModel.fetchCategories()
        }

        categoriesViewModel.categories.observe(viewLifecycleOwner, Observer {
            it.dropWhile { x -> x.name == "All" }
                .forEach { cat ->
                adapter.add(cat.name.toString())
                    categoriesHashMap[cat.name.toString()] = cat.id.toString()
            }
        })

        var autoCompleteCategories = view?.findViewById<AutoCompleteTextView>(R.id.post_category_autocomplete)
        autoCompleteCategories?.setAdapter(adapter)


        autoCompleteCategories!!.onItemClickListener = AdapterView.OnItemClickListener{
                    parent, _, position, _ ->
                var selectedCategoryName = parent.getItemAtPosition(position)
                categoryId = categoriesHashMap[selectedCategoryName]!!
                Log.i("Founded Category", categoryId)
            }




    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data?.data != null) {
            filePath = data.data!!
            var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, filePath)
            imageProduct.setImageBitmap(bitmap)
            var layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            layoutParams.setMargins(0,0,0,0)
            imageProduct.layoutParams = layoutParams
        }
    }

    private fun showFileChooser() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    companion object {
        fun newInstance(): PostProductFragment = PostProductFragment()
        private const val PICK_IMAGE_REQUEST = 123
    }
}