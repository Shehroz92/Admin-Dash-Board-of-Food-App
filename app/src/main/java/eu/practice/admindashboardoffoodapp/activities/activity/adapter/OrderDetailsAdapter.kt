package eu.practice.admindashboardoffoodapp.activities.activity.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.practice.admindashboardoffoodapp.databinding.OrderDetailsBinding

class OrderDetailsAdapter(
    private var context: Context,
    private var foodNames: ArrayList<String>,
    private var foodImage: ArrayList<String>,
    private var foodQuantities: ArrayList<Int>,
    private var foodPrices: ArrayList<String>

) : RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHOlder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHOlder {
        var binding = OrderDetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrderDetailsViewHOlder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHOlder, position: Int) {
       holder.bind(position)
    }
    override fun getItemCount(): Int {
        return  foodNames.size
    }

 inner class OrderDetailsViewHOlder(
        private val binding: OrderDetailsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                foodName.text = foodNames[position]
                foodPrice.text = foodPrices[position]
                foodQuantity.text = foodQuantities[position].toString()
                val uriString = foodImage[position]
                val uri = Uri.parse(uriString)
                Glide.with(context).load(uri).into(cartimage)

            }
        }

    }

}