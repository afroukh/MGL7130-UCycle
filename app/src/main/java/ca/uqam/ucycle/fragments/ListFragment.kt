package ca.uqam.ucycle.fragments

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ca.uqam.ucycle.Communicator
import ca.uqam.ucycle.R
import ca.uqam.ucycle.adapters.ListAdapter
import ca.uqam.ucycle.itemDecoration.SpacesItemDecoration
import ca.uqam.ucycle.models.Product
import ca.uqam.ucycle.repositories.ProductRepository
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment(), ListAdapter.ListListener {

  private val products = mutableListOf<Product>()

    lateinit var comm: Communicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        comm = activity as Communicator
        return inflater.inflate(R.layout.fragment_list, container, false)

    }

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here
        product_list.apply {
            // set a StaggeredGridLayoutManager to handle Android
            // RecyclerView behavior
            var spanCount = resources.getInteger( R.integer.gallery_columns)

            layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)

            val decoration = SpacesItemDecoration(16)
            addItemDecoration(decoration)

            if (products.isNullOrEmpty()) {

                val repo = ProductRepository()
                products.addAll(repo.getAllProduct())
            }

            // set the custom adapter to the RecyclerView
            adapter = ListAdapter(products, this@ListFragment)
        }
    }

    override fun onProductSelected(product: Product) {
        Log.i("CLICK", product.title)
        val productDetailFragment = ProductDetailFragment()
        comm.passDataCom(ProductDetailFragment.EXTRA_PRODUCT_TITLE, product.title, productDetailFragment)

    }

    companion object {
        fun newInstance(): ListFragment = ListFragment()
    }

}