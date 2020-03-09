package ca.uqam.ucycle.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ca.uqam.ucycle.R
import ca.uqam.ucycle.viewModels.CategoriesViewModel


class PostProductFragment : Fragment() {

    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var imageProduct: ImageView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)

        var rootView = inflater.inflate(R.layout.fragment_post_product, container, false)
        imageProduct = rootView.findViewById(R.id.post_product_image)


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imageProduct?.setOnClickListener {
            Log.i("My App", "Image View Clicked")
        }


        val adapter = ArrayAdapter<String>(activity,  android.R.layout.simple_list_item_1)

        if (categoriesViewModel.categories.value == null) {
            categoriesViewModel.fetchCategories()
        }

        categoriesViewModel.categories.observe(viewLifecycleOwner, Observer {
            it.dropWhile { x -> x.name == "All" }
                .forEach { cat ->
                adapter.add(cat.name.toString())
            }
        })

        var actv = view?.findViewById<AutoCompleteTextView>(R.id.post_category_autocomplete)
        actv?.setAdapter(adapter)
    }


    companion object {
        fun newInstance(): PostProductFragment = PostProductFragment()
    }
}