package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import eu.practice.admindashboardoffoodapp.R
import eu.practice.admindashboardoffoodapp.activities.activity.adapter.DeliveryAdapter
import eu.practice.admindashboardoffoodapp.databinding.ActivityMainBinding
import eu.practice.admindashboardoffoodapp.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {

    private val binding : ActivityOutForDeliveryBinding  by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val customerName = arrayListOf("Bilal" , "Ali" ,  "Umar")
        val moneyStatus = arrayListOf("Received","NotReceived","Pending")

        val adapter  = DeliveryAdapter(customerName,moneyStatus)

        binding.DeliveryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.DeliveryRecyclerView.adapter = adapter

        binding.root



    }
}