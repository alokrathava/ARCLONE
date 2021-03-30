package com.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.Cart
import com.razi.furnitar.R
import com.razi.furnitar.databinding.LayoutCartDataBinding
import com.utils.Config

class CartAdapter(private val cartInterface: CartInterface) :
        ListAdapter<Cart, CartAdapter.CustomerViewHolder>(DiffCallback()) {

    companion object {
        private const val TAG = "CartAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding =
                LayoutCartDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class CustomerViewHolder(private val binding: LayoutCartDataBinding) :
            RecyclerView.ViewHolder(binding.root) {


        init {

            binding.btnRemove.setOnClickListener {
                val position = adapterPosition
                val item = getItem(position)
                if (position != RecyclerView.NO_POSITION) {

                    cartInterface.onDelete(item)
                }
            }



            binding.edtQty.setOnItemClickListener { _, _, _, _ ->
                cartInterface.onChange(getItem(adapterPosition), binding.edtQty.text.toString().toInt())
            }

        }


        fun bind(cart: Cart) {

            binding.apply {
                txtProductName.text = cart.name
                txtProductPrice.text = "Rs. ${cart.productPrice}"


                Glide.with(root.context)
                        .load(Config.imageUrl + cart.image)
                        .into(imgProduct)


                val arrayAdapter = ArrayAdapter(
                        root.context,
                        android.R.layout.simple_list_item_1,
                        root.context.resources.getStringArray(R.array.qty)
                )

                edtQty.setText(cart.qty.toString())
                binding.edtQty.setAdapter(arrayAdapter)
                binding.edtQty.dismissDropDown()
            }
        }


    }

    class DiffCallback : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(old: Cart, aNew: Cart) =
                old.cartId == aNew.cartId

        override fun areContentsTheSame(old: Cart, aNew: Cart) =
                old == aNew
    }

    interface CartInterface {
        fun onDelete(cart: Cart)
        fun onChange(cart: Cart, qty: Int)
    }

}