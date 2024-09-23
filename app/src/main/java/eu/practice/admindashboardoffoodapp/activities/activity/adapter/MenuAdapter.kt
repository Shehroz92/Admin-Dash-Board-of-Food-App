package eu.practice.admindashboardoffoodapp.activities.activity.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import eu.practice.admindashboardoffoodapp.activities.activity.models.AllMenu
import eu.practice.admindashboardoffoodapp.databinding.AllItemsBinding

class MenuAdapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val itemQuantities = IntArray(menuList.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = AllItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class MenuViewHolder(private val binding: AllItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantities[position]
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)

                try {

                    cartfoodname.text = menuItem.foodName
                    cartItemprice.text = menuItem.foodPrice
                    description.text = menuItem.foodDescription
                    Glide.with(context).load(uri).into(cartimage)
                    cartItemQuantity.text = quantity.toString()
                } catch (e: Exception) {
                    Log.d("MenuAdapter", "Error binding data at position $position: ${e.message}")
                }

                minus.setOnClickListener {
                    decreaseQuantity(position)
                }
                plus.setOnClickListener {
                    increaseQuantity(position)
                }
                delete.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        deleteQuantity(itemPosition)
                    }
                }
            }
        }

        private fun decreaseQuantity(position: Int) {
            if (itemQuantities[position] > 1) {
                itemQuantities[position]--
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun increaseQuantity(position: Int) {
            if (itemQuantities[position] < 10) {
                itemQuantities[position]++
                binding.cartItemQuantity.text = itemQuantities[position].toString()
            }
        }

        private fun deleteQuantity(position: Int) {
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }
    }
}
