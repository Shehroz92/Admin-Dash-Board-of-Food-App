package eu.practice.admindashboardoffoodapp.activities.activity.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.practice.admindashboardoffoodapp.databinding.PendingOrderItemsBinding

class PendingAdapter(
    private val context: Context,
    private val customerName: MutableList<String>,
    private val foodQuantity: MutableList<String>,
    private val foodImage: MutableList<String>,
    private val itemClick: onItemClicked
    ) : RecyclerView.Adapter<PendingAdapter.PendingOrderViewHolder>(){
interface onItemClicked {
    fun onItemClickedListener(position: Int)
    fun onItemAcceptClickedListener(position: Int)
    fun onItemDispatchClickedListener(position: Int)



}

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
                val uriString  = (foodImage[position])
                Glide.with(context).load(uriString).into(cartimage)


                dispatch.apply {
                    if (!isAccepted){
                        text ="Accept"
                    }else{
                        text ="Dispatch"
                    }
                        setOnClickListener {
                            if (!isAccepted){
                                text = "Dispatch"
                                isAccepted = true
                                itemClick.onItemAcceptClickedListener(position)

                            }else{
                                customerName.removeAt((adapterPosition))
                                notifyItemRemoved(adapterPosition)
                                itemClick.onItemDispatchClickedListener(position)
                            }
                        }
                        itemView.setOnClickListener {
                        itemClick.onItemClickedListener(position)
                    }
                }
            }
        }
    }
}