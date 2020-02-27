package ca.uqam.ucycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.uqam.ucycle.R
import ca.uqam.ucycle.models.Product
import ca.uqam.ucycle.repositories.ProductRepository
import com.bumptech.glide.Glide

class ProductDetailFragment : Fragment() {

    private lateinit var mTitleView: TextView
    private lateinit var mPhotoView: ImageView
    private lateinit var mDescriptionView: TextView


    private val products = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        products.addAll(ProductRepository.PRODUCTS)

        var rootView = inflater.inflate(R.layout.fragment_product_detail, container, false)
        mTitleView = rootView.findViewById(R.id.product_title)
        mPhotoView = rootView.findViewById(R.id.product_main_photo)
        mDescriptionView = rootView.findViewById(R.id.product_description)


        var title = arguments?.getString(EXTRA_PRODUCT_TITLE)
        val product = products.find { x -> x.title == title }

        mTitleView.text = product?.title
        Glide.with(rootView.context).load(product?.photo).into(mPhotoView)
        mDescriptionView.text = product?.description

        return rootView
    }

    companion object {

        fun newInstance(): ProductDetailFragment = ProductDetailFragment()
        const val EXTRA_PRODUCT_TITLE = "ca.uqam.ucycle.extras.EXTRA_PRODUCT_TITLE"
    }
}