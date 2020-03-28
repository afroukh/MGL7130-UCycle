package ca.uqam.ucycle.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ca.uqam.ucycle.Communicator
import ca.uqam.ucycle.R
import ca.uqam.ucycle.data.Category
import ca.uqam.ucycle.data.City
import ca.uqam.ucycle.viewModels.CategoriesViewModel
import ca.uqam.ucycle.viewModels.CitiesViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup



class FiltersFragment : Fragment() {

    private lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var citiesViewModel: CitiesViewModel
    lateinit var comm: Communicator


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView = inflater.inflate(R.layout.fragment_filters, container, false)
        comm = activity as Communicator

        categoriesViewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        citiesViewModel = ViewModelProviders.of(this).get(CitiesViewModel::class.java)



        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = ArrayAdapter<String>(activity,  android.R.layout.simple_list_item_1)



        var chipGroup = view?.findViewById<ChipGroup>(R.id.category_chip_group)
        chipGroup!!.isSingleSelection = true

        if (categoriesViewModel.categories.value == null) {
            categoriesViewModel.fetchCategories()
        }

        categoriesViewModel.categories.observe(viewLifecycleOwner, Observer {
            it.forEach { cat ->
                chipGroup.addView(createChip(cat))
            }
            getProductList(Category("All", "All"))
        })

        if (citiesViewModel.cities.value == null) {
            citiesViewModel.fetchCities()
        }



        citiesViewModel.cities.observe(viewLifecycleOwner, Observer {
            it.forEach { cit ->
                adapter.add(cit.name.toString())
            }
        })

        var actv = view?.findViewById<AutoCompleteTextView>(R.id.cities)
        actv?.setAdapter(adapter)



    }



    private fun createChip(category: Category): Chip {
        val chip = Chip(context)
        chip.text = category.name
        chip.id = ViewCompat.generateViewId()
        chip.tag = category.id
        chip.isClickable = true
        chip.isCheckable = true
        chip.setOnClickListener {
            Log.i("CHIPS", chip.text as String? + " | " + chip.id + "|" + chip.tag)

           getProductList(Category(category.id, category.name))

        }
        return chip
    }

    private fun getProductList(category: Category) {
        val listFragment = ListFragment.newInstance()
        comm.passDataCom2(ListFragment.EXTRA_SELECTED_CATEGORY, category, listFragment)

    }

    private fun seedData() {
        listOf(
            City(name = "Montreal"),
                    City(name = "Toronto"),
                    City(name = "Calgary"),
                    City(name = "Vancouver"),
                    City(name = "Ottawa"),
                    City(name = "Quebec"),
                    City(name = "Saskatchewan"),
                    City(name = "Halifax"),
                    City(name = "Moncton"),
                    City(name = "Saint-John")
        ).forEach {
            citiesViewModel.addCity(it)
        }

        listOf(
            Category(name = "Pets"),
            Category(name = "Electronic"),
            Category(name = "Furniture"),
            Category(name = "Vintage")
        ).forEach {
            categoriesViewModel.addCategory(it)
        }
    }

    companion object {
        fun newInstance(): FiltersFragment = FiltersFragment()
    }


}