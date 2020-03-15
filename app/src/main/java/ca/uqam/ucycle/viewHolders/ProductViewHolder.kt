package ca.uqam.ucycle.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.uqam.ucycle.R
import ca.uqam.ucycle.adapters.ListAdapter
import ca.uqam.ucycle.data.Product
import com.bumptech.glide.Glide

class ProductViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_product, parent, false)) {
    private var mTitleView: TextView? = null
    private var mPhotoView: ImageView


    init {
        mTitleView = itemView.findViewById(R.id.product_title)
        mPhotoView = itemView.findViewById(R.id.product_photo)
    }

    fun bind(product: Product) {
        mTitleView?.text = product.title
        Glide.with(itemView.context).load(product.urlImage).into(mPhotoView)
        itemView.tag = product
    }

}