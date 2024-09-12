package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


        binding.backButton.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}