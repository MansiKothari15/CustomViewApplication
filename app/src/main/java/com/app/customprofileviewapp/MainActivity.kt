package com.app.customprofileviewapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.app.customprofileviewapp.databinding.ActivityFlexBinding
import com.app.customprofileviewapp.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
     val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            //start intent to flex activity
            startActivity(Intent(this, FlexActivity::class.java))
        }
        binding.button2.setOnClickListener {
            startActivity(Intent(this, DynamicLinearActivity::class.java))
        }
    }
}