package com.example.shop.adapter
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.databinding.ListItemBinding
import com.example.shop.retrofit.Product
import com.squareup.picasso.Picasso

class ProductAdapter(private val listener : Listener) : ListAdapter<Product, ProductAdapter.Holder>(
    Comparator()){

    class Holder(view : View) : RecyclerView.ViewHolder(view) {
        val binding = ListItemBinding.bind(view)

        fun bind(product: Product, listener: Listener) = with(binding) {
            Picasso.get().load(Uri.parse(product.images[0])).into(itemImage)
            itemTitle.text = product.title
            itemDescription.text = product.description
            itemRating.text = "⭐ ${product.rating}"
            itemPrice.text = "$${product.price}"
            val cardView: CardView = itemView.findViewById(R.id.cardView)

            if (product.isFavourite){
                itemIsFav.setImageResource(R.drawable.true_favorite)
            }
            else{
                itemIsFav.setImageResource(R.drawable.false_favorite)
            }

            itemImage.setOnClickListener {
                listener.onClickItem(product)
            }

            itemIsFav.setOnClickListener {
                when (product.isFavourite) {
                    true -> {
                        product.isFavourite = false
                        itemIsFav.setImageResource(R.drawable.false_favorite)
                    }
                    false -> {
                        product.isFavourite = true
                        itemIsFav.setImageResource(R.drawable.true_favorite)
                    }
                }
            }



        }
    }

    class Comparator : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), listener)

        holder.binding.cardView.setOnClickListener {
            val product: Product = getItem(position)
        }



    }

    interface Listener{

        fun onClickItem(product: Product)

    }

}