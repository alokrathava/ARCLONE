package com.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.Order
import com.razi.furnitar.databinding.LayoutHistoryBinding
import com.utils.Config

class OrderAdapter(private val orderInterface: OrderInterface) :
        ListAdapter<Order, OrderAdapter.CustomerViewHolder>(DiffCallback()) {

    companion object {
        private const val TAG = "OrderAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding =
                LayoutHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class CustomerViewHolder(private val binding: LayoutHistoryBinding) :
            RecyclerView.ViewHolder(binding.root) {


        init {

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {

                    orderInterface.onClick(getItem(position))
                }
            }

        }


        fun bind(order: Order) {

            binding.apply {
                tvProductName.text = order.productName
                tvAmount.text = "Rs. ${order.total}"
                tvQty.text = "Qty: ${order.qty}"
                tvDate.text = "${order.createdAt}"

                Glide.with(root.context)
                        .load(Config.imageUrl + order.productImg)
                        .into(productImage)

            }
        }


    }

    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(old: Order, aNew: Order) =
                old.productId == aNew.productId

        override fun areContentsTheSame(old: Order, aNew: Order) =
                old == aNew
    }

    interface OrderInterface {
        fun onClick(order: Order)
    }

}