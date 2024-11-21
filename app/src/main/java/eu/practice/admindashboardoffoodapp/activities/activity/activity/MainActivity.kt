package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import eu.practice.admindashboardoffoodapp.activities.activity.models.OrderDetails
import eu.practice.admindashboardoffoodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var completeOrderReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.textView8.setOnClickListener {
            val intent = Intent(this@MainActivity, PendingOrderActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.addMenuConstraint.setOnClickListener {
            val intent = Intent(this@MainActivity, AddItemActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.allMenuShow.setOnClickListener {
            val intent = Intent(this@MainActivity, AllItemActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.showProfile.setOnClickListener {
            val intent = Intent(this@MainActivity, AdminProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.newUserCreate.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateUserActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.OrderDispatch.setOnClickListener {
            val intent = Intent(this, OutForDeliveryActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.logOUtButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        pendingOrders()
        completeOrders()
        wholeTimeEarning()
    }

    private fun wholeTimeEarning() {

        val listOfTotalPay = mutableListOf<Int>()
        completeOrderReference = FirebaseDatabase.getInstance().reference.child("CompletedOrder")
        completeOrderReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for ( orderSnapshot in snapshot.children){
                    var completeOrder = orderSnapshot.getValue(OrderDetails::class.java)
                    completeOrder?.totalAmount?.replace("$","")?.toIntOrNull()
                        ?.let { i ->
                            listOfTotalPay.add(i)
                        }
                }
                binding.wholeTimeEarning.text = listOfTotalPay.sum().toString() + "$"
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun completeOrders() {
        val completedOrdersReference = database.reference.child("CompletedOrder")
        var completeOrderItemCount = 0
        completedOrdersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                completeOrderItemCount = snapshot.childrenCount.toInt()
                binding.completedOrder.text = completeOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun pendingOrders() {
        database = FirebaseDatabase.getInstance()
        val pendingOrdersReference = database.reference.child("OrderDetails")
        var pendingOrderItemCount = 0
        pendingOrdersReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pendingOrderItemCount = snapshot.childrenCount.toInt()
                binding.pendingOrders.text = pendingOrderItemCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
