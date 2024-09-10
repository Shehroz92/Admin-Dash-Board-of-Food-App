package eu.practice.admindashboardoffoodapp.activities.activity.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.practice.admindashboardoffoodapp.databinding.AllItemsBinding


class MenuAdapter (private val cartItems:MutableList<String> , private val cartItemPrice:MutableList<String> , private var cartImage: MutableList<Int>  ) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val itemQuantities = IntArray(cartItems.size) { 1 }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = AllItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }


    inner class MenuViewHolder(private val binding: AllItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                cartfoodname.text = cartItems[position]
                cartItemprice.text = cartItemPrice[position]
                cartimage.setImageResource(cartImage[position])
                cartItemQuantity.text = quantity.toString()

                minus.setOnClickListener {
                    decreaseQuantity(position)
                }
                plus.setOnClickListener {
                    increaseQuantity(position)

                }
                delete.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION){
                        deleteQuantity(itemPosition)
                    }


                }
            }
        }

        private  fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        private  fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun deleteQuantity(position: Int) {

            cartItems.removeAt(position)
            cartImage.removeAt(position)
            cartItemPrice.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)

        }
    }
}
