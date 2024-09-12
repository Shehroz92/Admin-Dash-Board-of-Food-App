package eu.practice.admindashboardoffoodapp.activities.activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.practice.admindashboardoffoodapp.databinding.PendingOrderItemsBinding

class PendingAdapter( private val customerName:ArrayList<String>,private val foodQuantity:ArrayList<String>,private val foodImage:ArrayList<Int> ) : RecyclerView.Adapter<PendingAdapter.PendingOrderViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrderViewHolder {
        val binding = PendingOrderItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  PendingOrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  customerName.size
    }

    override fun onBindViewHolder(holder: PendingOrderViewHolder, position: Int) {
        holder.bind(position)
    }

    inner  class PendingOrderViewHolder (
        private val binding : PendingOrderItemsBinding ):RecyclerView.ViewHolder(binding.root) {
            private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                cartfoodname.text = customerName[position]
                quantity.text= foodQuantity[position]
                cartimage.setImageResource(foodImage[position])

                dispatch.apply {

                }

            }
        }
    }
}