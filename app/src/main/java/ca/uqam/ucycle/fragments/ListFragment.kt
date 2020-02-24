package ca.uqam.ucycle.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ca.uqam.ucycle.R
import ca.uqam.ucycle.adapters.ListAdapter
import ca.uqam.ucycle.models.Product
import kotlinx.android.synthetic.main.fragment_list.*
import java.net.URL


class ListFragment : Fragment() {



    private val products = listOf(
        Product("Persian Cat", "https://i.picsum.photos/id/867/200/300.jpg"),
        Product("German Fisher", "https://i.picsum.photos/id/868/200/300.jpg"),
        Product("Old Desk","https://i.picsum.photos/id/869/100/300.jpg"),
        Product("Dryer", "https://i.picsum.photos/id/870/200/300.jpg"),
        Product("Winter Tires", "https://i.picsum.photos/id/871/200/300.jpg"),
        Product("Vintage Hour", "https://i.picsum.photos/id/872/200/300.jpg"),
        Product("Baseball Cards", "https://i.picsum.photos/id/873/200/300.jpg")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here
        product_list.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            // set the custom adapter to the RecyclerView
            adapter = ListAdapter(products)
        }
    }


    companion object {
        fun newInstance(): ListFragment = ListFragment()
    }

    private fun getImgBitmap(urlText: String): Bitmap {
        var url = URL(urlText)
        var bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
        return bmp
    }
}