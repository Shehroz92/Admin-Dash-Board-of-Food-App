package eu.practice.admindashboardoffoodapp.activities.activity.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.practice.admindashboardoffoodapp.R
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

    }
}
