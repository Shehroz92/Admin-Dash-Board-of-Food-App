package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import eu.practice.admindashboardoffoodapp.activities.activity.models.UserModel
import eu.practice.admindashboardoffoodapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth            :FirebaseAuth
    private lateinit var email           :String
    private lateinit var password        :String
    private lateinit var userName        :String
    private lateinit var restaurantName  :String
    private lateinit var dataBase        :DatabaseReference


    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // initialize Fire Base Auth
           auth =Firebase.auth
        // initialize Fire Base Data Base
           dataBase = Firebase.database.reference

        binding.createButton.setOnClickListener {

            userName = binding.nameOfOwner.text.toString().trim()
            restaurantName = binding.nameOfResturant.text.toString().trim()
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (userName.isBlank() || restaurantName.isBlank() || email.isBlank() || password.isBlank()){
                Toast.makeText(this,"Please fill all details",Toast.LENGTH_SHORT).show()
            }else{
                createAccount(email,password)
            }

        }
        binding.alreadyHaveAnAccount.setOnClickListener {
            val intent  = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val locationList = arrayOf("Karachi", "Lahore", "Multan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationList)
        val autoCompleteTextView = binding.listofLocation
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            task -> if (task.isSuccessful){
            Toast.makeText(this,"Account Created",Toast.LENGTH_SHORT).show()
            saveUserData()

            val intent  = Intent(this@SignUpActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this,"Account Creation Failed",Toast.LENGTH_SHORT).show()
            Log.d("Account","create Account : Failure",task.exception)
        }


        }
    }
// save data in to Data Base
    private fun saveUserData() {

        userName = binding.nameOfOwner.text.toString().trim()
        restaurantName = binding.nameOfResturant.text.toString().trim()
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(userName,restaurantName,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        // save user data  FireBase
        dataBase.child("user").child(userId).setValue(user)

    }
}
