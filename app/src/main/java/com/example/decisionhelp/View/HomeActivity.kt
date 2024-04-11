package com.example.decisionhelp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.decisionhelp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.EditBtn.setOnClickListener {
            intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        binding.MyPageBtn.setOnClickListener {
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}