package eu.practice.admindashboardoffoodapp.activities.activity.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import eu.practice.admindashboardoffoodapp.R
import eu.practice.admindashboardoffoodapp.activities.activity.adapter.PendingAdapter
import eu.practice.admindashboardoffoodapp.databinding.ActivityPendingOrderBinding


class PendingOrderActivity : AppCompatActivity() {

    private val binding:ActivityPendingOrderBinding by lazy {
        ActivityPendingOrderBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        val foodName = listOf("Burger","Pasta","Pizza","Momo","sandwich","Platter")
        val foodQuantity = listOf("5","6","7","8","9","10",)
        val foodImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3
        )

        val adapter = PendingAdapter(ArrayList(foodName),ArrayList(foodQuantity),ArrayList(foodImage))

        binding.PendingRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.PendingRecyclerView.adapter=adapter


    }
}