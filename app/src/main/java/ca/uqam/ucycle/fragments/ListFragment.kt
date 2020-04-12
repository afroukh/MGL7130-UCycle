package ca.uqam.ucycle.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ca.uqam.ucycle.Communicator
import ca.uqam.ucycle.R
import ca.uqam.ucycle.adapters.ListAdapter
import ca.uqam.ucycle.data.Category
import ca.uqam.ucycle.itemDecoration.SpacesItemDecoration
import ca.uqam.ucycle.data.Product
import ca.uqam.ucycle.viewModels.ProductsViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), ListAdapter.ListListener {

    private val products = mutableListOf<Product>()
    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var productList: RecyclerView
    private lateinit var category: Category
    private lateinit var comm: Communicator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // retainInstance = true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_list, container, false)

        productList = rootView.findViewById(R.id.product_list)
        initializeRecyclerView(productList)

        comm = activity as Communicator


        category = if (arguments?.getParcelable<Category>(EXTRA_SELECTED_CATEGORY) == null)
            Category("All", "All")
        else arguments?.getParcelable(EXTRA_SELECTED_CATEGORY)!!


        return rootView

    }


    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("onViewCreated", "Called")

        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

        Log.i("categoryId", category.id)

        if (category.name == "All") {
            productsViewModel.fetchAllProducts()

        } else {
            productsViewModel.fetchProducts(category)
        }


        productsViewModel.products.observe(viewLifecycleOwner, Observer<List<Product>> {

            // set the custom adapter to the RecyclerView
            productList.adapter = ListAdapter(it, this@ListFragment)

            productList.adapter!!.notifyDataSetChanged()

        })


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_add_product.setOnClickListener {

            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, PostProductFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }


    override fun onProductSelected(product: Product) {
        Log.i("CLICK", product.title)
        val productDetailFragment = ProductDetailFragment()
        comm.passDataCom(ProductDetailFragment.EXTRA_PRODUCT, product, productDetailFragment)

    }


    private fun initializeRecyclerView(recyclerView: RecyclerView) {
        // set a StaggeredGridLayoutManager to handle Android
        // RecyclerView behavior
        var spanCount = resources.getInteger(R.integer.gallery_columns)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        val decoration = SpacesItemDecoration(16)
        recyclerView.addItemDecoration(decoration)


    }

    companion object {
        fun newInstance(): ListFragment = ListFragment()
        const val EXTRA_SELECTED_CATEGORY = "ca.uqam.ucycle.extras.EXTRA_SELECTED_CATEGORY"
    }

}