package eu.practice.admindashboardoffoodapp.activities.activity.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import eu.practice.admindashboardoffoodapp.activities.activity.adapter.OrderDetailsAdapter
import eu.practice.admindashboardoffoodapp.activities.activity.models.OrderDetails
import eu.practice.admindashboardoffoodapp.databinding.ActivityOrderDetailsBinding


class OrderDetailsActivity : AppCompatActivity() {
    private val binding: ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }

    private var userName: String? = null
    private var address: String? = null
    private var phoneNumber: String? = null
    private var totalPrice: String? = null
    private var foodNames: ArrayList<String> = arrayListOf()
    private var foodImages: ArrayList<String> = arrayListOf()
    private var foodQuantities: ArrayList<Int> = arrayListOf()
    private var foodPrices: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backbutton.setOnClickListener {
            finish()
        }

        getDataFromIntent()


    }

    private fun getDataFromIntent() {
        val receivedOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receivedOrderDetails.let { orderDetails ->
            userName = receivedOrderDetails.userName
            foodNames = receivedOrderDetails.foodNames as ArrayList<String>
            foodImages = receivedOrderDetails.foodImage as ArrayList<String>
            foodQuantities = receivedOrderDetails.foodQuantities as ArrayList<Int>
            address = receivedOrderDetails.address
            phoneNumber = receivedOrderDetails.phoneNumber
            foodPrices = receivedOrderDetails.foodPrices as ArrayList<String>

            setUserData()
            setAdapter()
        }
    }

    private fun setAdapter() {
        binding.orderDetailsRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this, foodNames, foodImages, foodQuantities, foodPrices)
        binding.orderDetailsRecyclerView.adapter = adapter

    }

    private fun setUserData() {
        binding.name.text = userName
        binding.phone.text = phoneNumber
        binding.address.text = address
        binding.totalPay.text = totalPrice

    }
}