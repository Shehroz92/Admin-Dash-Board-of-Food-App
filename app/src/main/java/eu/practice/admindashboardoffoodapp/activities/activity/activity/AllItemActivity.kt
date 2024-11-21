package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
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
    private var menuItems: ArrayList<AllMenu> = ArrayList()

    // Lazy initialization of the binding object
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Initialize Firebase database and reference
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference

        // Retrieve and display menu items

        retrieveMenuItem()

        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun retrieveMenuItem() {

        database = FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference = database.getReference("menu")

        // Fetch data from Firebase
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data before populating
                 menuItems.clear()

                // Loop through each item and add to the list
                Log.d("DatabaseSnapshot", "Children count: ${snapshot.childrenCount}")
                for (foodSnapshot :DataSnapshot? in snapshot.children) {
                    val menuItem : AllMenu? = foodSnapshot?.getValue(AllMenu::class.java)
                    Log.d("DatabaseItem", "Menu Item: ${foodSnapshot?.key} -> $menuItem")
                    menuItem?.let {
                        menuItems.add(it)
                    } ?: Log.d("DatabaseError", "Failed to parse item: ${foodSnapshot?.key}")
                }
                    setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message}")
            }
        })
    }

    private fun setAdapter() {
        val adapter = MenuAdapter(this@AllItemActivity, menuItems, databaseReference){
            position -> deleteMenuItems(position)
        }
        binding.MenuRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MenuRecyclerView.adapter = adapter

    }

    private fun deleteMenuItems(position:Int) {
        val menuItemToDelete = menuItems[position]
        val menuItemKey = menuItemToDelete.key
        val foodMenuReference = database.reference.child("menu").child(menuItemKey!!)
        foodMenuReference.removeValue().addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                menuItems.removeAt(position)
                binding.MenuRecyclerView.adapter?.notifyItemRemoved(position)
            }else{
                Toast.makeText(this, "item not Delete", Toast.LENGTH_SHORT).show()
            }
        }
    }
}