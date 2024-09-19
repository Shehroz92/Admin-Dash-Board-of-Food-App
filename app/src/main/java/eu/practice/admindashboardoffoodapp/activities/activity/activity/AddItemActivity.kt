package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import eu.practice.admindashboardoffoodapp.activities.activity.models.AllMenu
import eu.practice.admindashboardoffoodapp.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    // Food Items details
    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private lateinit var foodIngredient: String
    private var foodImageUri: Uri? = null

    // Firebase instances
    private lateinit var auth: FirebaseAuth
    private lateinit var dataBase: FirebaseDatabase

    private lateinit var binding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance()
        dataBase = FirebaseDatabase.getInstance()

        binding.AddItemButtton.setOnClickListener {
            // Get Data from input fields
            foodName = binding.foodName.text.toString().trim()
            foodPrice = binding.foodPrice.text.toString().trim()
            foodDescription = binding.description.text.toString().trim()
            foodIngredient = binding.ingredients.text.toString().trim()

            if (foodName.isNotBlank() && foodPrice.isNotBlank() && foodDescription.isNotBlank() && foodIngredient.isNotBlank()) {
                if (auth.currentUser != null) {
                    uploadData()
                } else {
                    Toast.makeText(this, "Please log in first", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fill all the details", Toast.LENGTH_SHORT).show()
            }
        }

        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun uploadData() {
        // Get a reference to the "menu" node in the database
        val menuRef: DatabaseReference = dataBase.getReference("menu_items")
        // Generate a unique key for the menu item
        val newItemKey: String? = menuRef.push().key

        if (foodImageUri != null) {
            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadTask = imageRef.putFile(foodImageUri!!)

            uploadTask.addOnSuccessListener {

                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->

                    val newItem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadUri.toString()
                    )
                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newItem).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
                            } else {

                                Toast.makeText(this, "Data upload failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }.addOnFailureListener { _ ->
                   
                    Toast.makeText(this, "Failed to get image URL", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener { _ ->
              
                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    // Image Picker
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.selectedImage.setImageURI(uri)
            foodImageUri = uri
        } else {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }
}
