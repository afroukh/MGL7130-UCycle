package ca.uqam.ucycle.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ca.uqam.ucycle.R
import kotlinx.android.synthetic.main.fragment_product_detail.view.*

class ProductDetailFragment : Fragment() {

    private var mTitleView: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        var rootView = inflater.inflate(R.layout.fragment_product_detail, container, false)
        var title = arguments?.getString(EXTRA_PRODUCT_TITLE)
        rootView?.title_product?.text = title
        return rootView
    }

    companion object {

        fun newInstance(): ProductDetailFragment = ProductDetailFragment()
        const val EXTRA_PRODUCT_TITLE = "ca.uqam.ucycle.extras.EXTRA_PRODUCT_TITLE"
    }
}