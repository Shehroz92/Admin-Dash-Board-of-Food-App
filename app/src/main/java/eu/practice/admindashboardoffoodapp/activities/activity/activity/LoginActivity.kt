package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import eu.practice.admindashboardoffoodapp.R
import eu.practice.admindashboardoffoodapp.activities.activity.models.UserModel
import eu.practice.admindashboardoffoodapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var userName: String? = null
    private var restaurantName: String? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Web client ID from Google API
            .requestEmail() // Request email
            .build()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // Initialize Firebase Database
        database = Firebase.database.reference
        // Initialize Google Sign-In client
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        binding.goToSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.googleButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }

        binding.logInButton.setOnClickListener {
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                createUserAccount(email, password)
            }
        }
    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user: FirebaseUser? = auth.currentUser
                updateUi(user)
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { createTask ->
                        if (createTask.isSuccessful) {
                            val user: FirebaseUser? = auth.currentUser
                            saveUserData()
                            updateUi(user)
                        } else {
                            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                            Log.e("Account", "createUserAccount: Authentication failed", createTask.exception)
                        }
                    }
            }
        }
    }

    private fun saveUserData() {
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()

        val user = UserModel(userName, restaurantName, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        // Save user data to Firebase
        userId.let {
            database.child("user").child(userId).setValue(user)
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account: GoogleSignInAccount? = task.getResult(Exception::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            // Successfully signed in with Google
                            val user: FirebaseUser? = auth.currentUser
                            Toast.makeText(this, "Sign in Successful", Toast.LENGTH_SHORT).show()
                            updateUi(user)
                        } else {
                            // Handle sign-in failure
                            Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show()
                            Log.e("GoogleSignIn", "signInWithCredential:failure", authTask.exception)
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle Google sign-in error
                Toast.makeText(this, "Google Sign-in Failed", Toast.LENGTH_SHORT).show()
                Log.e("GoogleSignIn", "getSignedInAccountFromIntent:exception", e)
            }
        }
    }

    // Check if user is already log in
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser!=null){
               startActivity(Intent(this,MainActivity::class.java))
               finish()
        }
    }
}
