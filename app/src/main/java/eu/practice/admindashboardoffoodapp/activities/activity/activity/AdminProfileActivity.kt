package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.practice.admindashboardoffoodapp.R
import eu.practice.admindashboardoffoodapp.databinding.ActivityAdminProfileBinding

class AdminProfileActivity : AppCompatActivity() {

    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.name.isEnabled = false
        binding.Address.isEnabled = false
        binding.Email.isEnabled = false
        binding.Phone.isEnabled = false
        binding.Password.isEnabled = false

        var isEnabled = false
         binding.editTextAll.setOnClickListener {
             isEnabled =! isEnabled

             binding.name.isEnabled = isEnabled
             binding.Address.isEnabled = isEnabled
             binding.Email.isEnabled = isEnabled
             binding.Phone.isEnabled = isEnabled
             binding.Password.isEnabled = isEnabled

         }
        if (isEnabled){
            binding.name.requestFocus()

        }




    }
}