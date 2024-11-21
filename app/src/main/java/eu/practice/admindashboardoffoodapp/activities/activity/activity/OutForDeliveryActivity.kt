package eu.practice.admindashboardoffoodapp.activities.activity.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import eu.practice.admindashboardoffoodapp.activities.activity.adapter.DeliveryAdapter
import eu.practice.admindashboardoffoodapp.activities.activity.models.OrderDetails
import eu.practice.admindashboardoffoodapp.databinding.ActivityOutForDeliveryBinding

class OutForDeliveryActivity : AppCompatActivity() {
    private val binding : ActivityOutForDeliveryBinding  by lazy {
        ActivityOutForDeliveryBinding.inflate(layoutInflater)
    }
    private lateinit  var database: FirebaseDatabase
    private var listOfCompleteOrderDetails : ArrayList<OrderDetails> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }
        retrieveCompleteOrderDetails()
        binding.root
    }

    private fun retrieveCompleteOrderDetails() {
        database = FirebaseDatabase.getInstance()
        val completeOrderReference = database.reference.child("CompletedOrder").orderByChild("currentTime")
        completeOrderReference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listOfCompleteOrderDetails.clear()

                for (orderSnapshot in snapshot.children){
                    val completeOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.let {
                        listOfCompleteOrderDetails.add(it)
                    }
                }
                listOfCompleteOrderDetails.reverse()
                setDataIntoRecyclerView()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setDataIntoRecyclerView() {
        val customerName = mutableListOf<String>()
        val moneyStatus = mutableListOf<Boolean>()
        for (order in listOfCompleteOrderDetails) {
            order.userName?.let { customerName.add(it) }
            moneyStatus.add(order.paymentReceived)
        }
            val adapter = DeliveryAdapter(customerName, moneyStatus)
            binding.DeliveryRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.DeliveryRecyclerView.adapter = adapter
            listOfCompleteOrderDetails.reverse()

    }
}