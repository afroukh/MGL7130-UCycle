package ca.uqam.ucycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ca.uqam.ucycle.R
import ca.uqam.ucycle.data.Product
import ca.uqam.ucycle.viewModels.ProductsViewModel
import com.bumptech.glide.Glide

class ProductDetailFragment : Fragment() {

    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var mTitleView: TextView
    private lateinit var mPhotoView: ImageView
    private lateinit var mDescriptionView: TextView
    private var isImageFitToScreen: Boolean = false
    private lateinit var extraProduct: Product

    private val products = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        productsViewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

        var rootView = inflater.inflate(R.layout.fragment_product_detail, container, false)
        mTitleView = rootView.findViewById(R.id.product_title)
        mPhotoView = rootView.findViewById(R.id.product_main_photo)
        mDescriptionView = rootView.findViewById(R.id.product_description)


        var extraProduct = arguments?.getParcelable<Product>(EXTRA_PRODUCT)


        productsViewModel.getProduct(extraProduct?.id!!, extraProduct.categoryId!!)


        productsViewModel.product.observe(viewLifecycleOwner, Observer {
            mTitleView.text = it?.title
            Glide.with(rootView.context).load(it?.urlImage).into(mPhotoView)
            mDescriptionView.text = it?.description
        })




                //    val product = products.find { x -> x.title == title }


//        mPhotoView.setOnClickListener{
//            if (isImageFitToScreen) {
//                isImageFitToScreen = false
//                mPhotoView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, R.dimen.img_layout_height)
//                mPhotoView.adjustViewBounds = true
//            } else {
//                isImageFitToScreen = true
//                mPhotoView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
//                mPhotoView.scaleType = ImageView.ScaleType.FIT_XY
//            }
//        }


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object {

        fun newInstance(): ProductDetailFragment = ProductDetailFragment()
        const val EXTRA_PRODUCT = "ca.uqam.ucycle.extras.EXTRA_PRODUCT"
    }
}