package ca.uqam.ucycle.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.uqam.ucycle.R
import ca.uqam.ucycle.models.Product
import ca.uqam.ucycle.viewHolders.ProductViewHolder

class ListAdapter(private val list: List<Product>, private val listListener: ListListener)
    : RecyclerView.Adapter<ProductViewHolder>(), View.OnClickListener {



    interface ListListener {
        fun onProductSelected(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product: Product = list[position]
        holder.itemView.setOnClickListener(this@ListAdapter)
        holder.bind(product)
    }

    override fun getItemCount(): Int = list.size

    override fun onClick(itemView: View) {
       when(itemView.id) {
           R.id.card_view -> listListener.onProductSelected(itemView.tag as Product)
       }
    }

}