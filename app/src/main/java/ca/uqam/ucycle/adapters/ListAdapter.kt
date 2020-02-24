package ca.uqam.ucycle.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ca.uqam.ucycle.models.Product
import ca.uqam.ucycle.viewHolders.ProductViewHolder

class ListAdapter(private val list: List<Product>) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product: Product = list[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = list.size



}