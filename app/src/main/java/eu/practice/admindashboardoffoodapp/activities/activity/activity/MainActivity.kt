package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import eu.practice.admindashboardoffoodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMenuConstraint.setOnClickListener {
            val intent = Intent(this@MainActivity,AddItemActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.allMenuShow.setOnClickListener{
            val intent = Intent(this@MainActivity , AllItemActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.showProfile.setOnClickListener {
            Toast.makeText(this,"Show Profile is click ",Toast.LENGTH_SHORT).show()
        }

        binding.newUserCreate.setOnClickListener {
            Toast.makeText(this,"New Create User is click ",Toast.LENGTH_SHORT).show()
        }

        binding.OrderDispatch.setOnClickListener {
            val intent = Intent(this , OutForDeliveryActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this,"Order Dispatch is click ",Toast.LENGTH_SHORT).show()
        }

        binding.showProfile.setOnClickListener {
            Toast.makeText(this,"Order Dispatch is click ",Toast.LENGTH_SHORT).show()
        }
        binding.logOUtButton.setOnClickListener {
            Toast.makeText(this,"Log Out is click ",Toast.LENGTH_SHORT).show()
        }
    }
}
