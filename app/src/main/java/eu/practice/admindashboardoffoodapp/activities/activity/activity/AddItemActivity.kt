package eu.practice.admindashboardoffoodapp.activities.activity.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import eu.practice.admindashboardoffoodapp.R
import eu.practice.admindashboardoffoodapp.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}