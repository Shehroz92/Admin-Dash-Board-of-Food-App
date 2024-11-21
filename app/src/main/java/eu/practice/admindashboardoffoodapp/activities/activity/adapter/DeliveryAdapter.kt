package eu.practice.admindashboardoffoodapp.activities.activity.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.practice.admindashboardoffoodapp.databinding.DeliveryItemsBinding


class DeliveryAdapter(
    private val customerName: MutableList<String>,
    private val moneyStatus: MutableList<Boolean>

) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding =
            DeliveryItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return customerName.size
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class DeliveryViewHolder(
        private val binding: DeliveryItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.customerName.text = customerName[position]
            if (moneyStatus[position] == true) {
                binding.moneyStatus.text = "Received"
            }else{
                binding.moneyStatus.text = "Not Received"
            }

            val colorMap = mapOf(
                true to Color.GREEN,
                false to Color.RED
            )
            binding.moneyStatus.setTextColor(colorMap[moneyStatus[position]] ?: Color.BLACK)
            binding.statusColour.backgroundTintList =
                ColorStateList.valueOf(colorMap[moneyStatus[position]] ?: Color.BLACK)


        }
    }
}