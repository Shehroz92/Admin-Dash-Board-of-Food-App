package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import eu.practice.admindashboardoffoodapp.activities.activity.adapter.MenuAdapter
import eu.practice.admindashboardoffoodapp.activities.activity.models.AllMenu
import eu.practice.admindashboardoffoodapp.databinding.ActivityAllItemBinding

class AllItemActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private  var menuItems:ArrayList<AllMenu> = ArrayList()

    private  val binding : ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        databaseReference = FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

        binding.backButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.root

    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val foodRef =database.reference.child("menu")


        // fetch data from data base
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // clear existing data before populating
                 menuItems.clear()
                // loop for each through item
                for (foodSnapShoot in snapshot.children){
                    val menuItem  = foodSnapShoot.getValue(AllMenu::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError","Error: ${error.message}")
            }


        })

    }

    private fun setAdapter() {
        val adapter = MenuAdapter(this@AllItemActivity,menuItems,databaseReference)
        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter=adapter
    }
}