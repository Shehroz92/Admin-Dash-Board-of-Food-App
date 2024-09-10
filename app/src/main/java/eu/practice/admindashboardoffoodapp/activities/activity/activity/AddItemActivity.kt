package eu.practice.admindashboardoffoodapp.activities.activity.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import eu.practice.admindashboardoffoodapp.R
import eu.practice.admindashboardoffoodapp.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectImage.setOnClickListener {
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.backButton.setOnClickListener {
            finish()
        }

    }

        val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
            uri -> if (uri !=null){
                binding.selectedImage.setImageURI(uri)
            }
        }
}
