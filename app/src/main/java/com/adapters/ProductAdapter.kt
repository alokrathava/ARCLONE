package com.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.Product
import com.razi.furnitar.databinding.LayoutProductBinding
import com.utils.Config
import java.util.*

class ProductAdapter(private val productInterface: ProductInterface) :
        ListAdapter<Product, ProductAdapter.CustomerViewHolder>(DiffCallback()), Filterable {

    companion object {
        private const val TAG = "ProductAdapter"
    }

    private lateinit var mList: List<Product>
    private lateinit var filteredList: List<Product>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding =
                LayoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class CustomerViewHolder(private val binding: LayoutProductBinding) :
            RecyclerView.ViewHolder(binding.root) {


        init {

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    productInterface.onClick(getItem(position))
                }
            }

        }


        fun bind(product: Product) {

            binding.apply {
                tvProductName.text = product.name
                tvPrice.text = "Rs. ${product.price}"


                Log.d(TAG, "bind: ${Config.imageUrl + product.image}")
                Glide.with(root.context)
                        .load(Config.imageUrl + product.image)
                        .into(productImage)
            }
        }


    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(old: Product, aNew: Product) =
                old.productId == aNew.productId

        override fun areContentsTheSame(old: Product, aNew: Product) =
                old == aNew
    }

    interface ProductInterface {
        fun onClick(product: Product)
    }


    fun modifyList(list: List<Product>) {
        mList = list
        filteredList = list

        submitList(list)
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {
                    filteredList = mList
                } else {
                    mList.let {
                        val mFilteredList = mutableListOf<Product>()
                        for (item in mList) {
                            if (charString.toLowerCase(Locale.ENGLISH) in item.name.toLowerCase(
                                            Locale.ENGLISH
                                    )
                            ) {
                                mFilteredList.add(item)
                            }
                        }

                        filteredList = mFilteredList
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(
                    charSequence: CharSequence,
                    filterResults: FilterResults,
            ) {
                filteredList = filterResults.values as List<Product>
                submitList(filteredList)
            }
        }
    }
}