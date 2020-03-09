package ca.uqam.ucycle.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ca.uqam.ucycle.R
import ca.uqam.ucycle.data.Category
import ca.uqam.ucycle.viewModels.CategoriesViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class FiltersFragment : Fragment() {

    lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_filters, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        listOf(
//            Category(name = "Pets"),
//            Category(name = "Electronic"),
//            Category(name = "Furniture"),
//            Category(name = "Vintage")
//        ).forEach {
//            viewModel.addCategory(it)
//        }


        var chipGroup = view?.findViewById<ChipGroup>(R.id.category_chip_group)
        chipGroup!!.isSingleSelection = true

        if (viewModel.categories.value == null) {
            viewModel.fetchCategories()
        }

        viewModel.categories.observe(viewLifecycleOwner, Observer {
            it.forEach { cat ->
                chipGroup.addView(createChip(cat))
            }
        })



    }



    private fun createChip(category: Category): Chip {
        val chip = Chip(context)
        chip.text = category.name
        chip.id = ViewCompat.generateViewId()
        chip.isClickable = true
        chip.isCheckable = true
        chip.setOnClickListener {
            Log.i("CHIPS", chip.text as String? + " | " + chip.id)
        }
        return chip
    }

    companion object {
        fun newInstance(): FiltersFragment = FiltersFragment()
    }


}