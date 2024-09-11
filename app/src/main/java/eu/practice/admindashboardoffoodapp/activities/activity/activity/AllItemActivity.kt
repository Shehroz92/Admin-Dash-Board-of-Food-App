package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import eu.practice.admindashboardoffoodapp.R
import eu.practice.admindashboardoffoodapp.activities.activity.adapter.MenuAdapter
import eu.practice.admindashboardoffoodapp.databinding.ActivityAllItemBinding
import eu.practice.admindashboardoffoodapp.databinding.AllItemsBinding

class AllItemActivity : AppCompatActivity() {

    private  val binding : ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val cartFoodName = listOf("Burger","Pasta","Pizza","Momo","sandwich","Platter")
        val cartItemPrice = listOf("$50","$60","$70","$80","$90","$100",)
        val cartImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3
        )

        binding.backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        val adapter = MenuAdapter(ArrayList(cartFoodName),ArrayList(cartItemPrice),ArrayList(cartImage))
        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter=adapter

        binding.root

    }
}