package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import eu.practice.admindashboardoffoodapp.activities.activity.models.UserModel
import eu.practice.admindashboardoffoodapp.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {

    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var adminReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")

        binding.name.isEnabled = false
        binding.Address.isEnabled = false
        binding.Email.isEnabled = false
        binding.Phone.isEnabled = false
        binding.Password.isEnabled = false
        binding.button4.isEnabled = false

        var isEnabled = false
        binding.editTextAll.setOnClickListener {
            isEnabled = !isEnabled

            binding.name.isEnabled = isEnabled
            binding.Address.isEnabled = isEnabled
            binding.Email.isEnabled = isEnabled
            binding.Phone.isEnabled = isEnabled
            binding.Password.isEnabled = isEnabled

        }
        if (isEnabled) {
            binding.name.requestFocus()
        }
        binding.button4.isEnabled = isEnabled
        binding.button4.setOnClickListener {
            updateUserData()
        }

        retrieveUserData()
    }


    private fun retrieveUserData() {
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null) {
            val userReference = adminReference.child(currentUserUid)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var ownerName = snapshot.child("name").getValue()
                        var email = snapshot.child("email").getValue()
                        var password = snapshot.child("password").getValue()
                        var address = snapshot.child("address").getValue()
                        var phone = snapshot.child("phone").getValue()
                        setDataToTextView(ownerName, email, password, address, phone)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?
    ) {
        binding.name.setText(ownerName.toString())
        binding.Password.setText(password.toString())
        binding.Phone.setText(phone.toString())
        binding.Email.setText(email.toString())
        binding.Address.setText(address.toString())

    }

    private fun updateUserData() {
        val updateName = binding.name.text.toString()
        val updateEmail = binding.Password.text.toString()
        val updatePassword = binding.Phone.text.toString()
        val updatePhone = binding.Email.text.toString()
        val updateAddress = binding.Address.text.toString()
        val userData = UserModel(updateName,updateEmail,updatePassword,updatePhone,updateAddress)
        adminReference.setValue(userData).addOnCompleteListener {
            Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        }.addOnFailureListener {
            Toast.makeText(this, "Profile Updated fail", Toast.LENGTH_SHORT).show()
        }

    }
}