package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import eu.practice.admindashboardoffoodapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.textView8.setOnClickListener {
            val intent = Intent(this@MainActivity,PendingOrderActivity::class.java)
            startActivity(intent)
            finish()
        }

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
            val intent = Intent(this@MainActivity , AdminProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.newUserCreate.setOnClickListener {
            val intent = Intent(this@MainActivity , CreateUserActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.OrderDispatch.setOnClickListener {
            val intent = Intent(this , OutForDeliveryActivity::class.java)
            startActivity(intent)
            finish()

        }

        binding.logOUtButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
