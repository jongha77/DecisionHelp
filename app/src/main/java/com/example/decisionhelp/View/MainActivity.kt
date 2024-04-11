package com.example.decisionhelp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.decisionhelp.Model.UserViewModel
import com.example.decisionhelp.R
import com.example.decisionhelp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            val id = binding.idText.text.toString()
            val password = binding.passwordText.text.toString()
            viewModel.login(id, password)
        }

        viewModel.loginResult.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "로그인 실패ㅠ", Toast.LENGTH_SHORT).show()
            }
        })
    }
}