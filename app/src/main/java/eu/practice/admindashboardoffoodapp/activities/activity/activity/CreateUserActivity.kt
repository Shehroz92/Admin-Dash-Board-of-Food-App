package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import eu.practice.admindashboardoffoodapp.R
import eu.practice.admindashboardoffoodapp.databinding.ActivityCreateUserBinding
import eu.practice.admindashboardoffoodapp.databinding.ActivityLoginBinding

class CreateUserActivity : AppCompatActivity() {

    private val binding: ActivityCreateUserBinding by lazy {
        ActivityCreateUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.logInButton.setOnClickListener {
            Toast.makeText(this, "User Created Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@CreateUserActivity , MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}