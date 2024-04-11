package com.example.decisionhelp.View

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.example.decisionhelp.Model.UserViewModel
import com.example.decisionhelp.databinding.ActivitySignupBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: UserViewModel by viewModel()
    private var idCheck : Boolean = false
    private var pwCheck : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ID 입력시 실시간으로 ID 중복 확인
        binding.idText.addTextChangedListener { editable ->
            val id = editable.toString()
            viewModel.checkId(id)
        }

        // Sign up button click listener
        binding.signupBtn.setOnClickListener {
            val id = binding.idText.text.toString()
            val password = binding.passwordText.text.toString()
            if (!idCheck) {
                Toast.makeText(this, "이미 사용 중인 아이디 입니다.", Toast.LENGTH_SHORT).show()
            } else if (!pwCheck) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다..", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signUp(id, password)
            }
        }


        // Observe signup result
        viewModel.signupResult.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                // Handle successful signup, navigate to next screen etc.
            } else {
                Toast.makeText(this, "회원가입 실패ㅠ", Toast.LENGTH_SHORT).show()
                // Handle failed signup
            }
        })

        // Observe ID check result
        viewModel.idCheckResult.observe(this, Observer { idExists ->
            if (binding.idText.text.isBlank()) {
                binding.idCheckText.text = "" // Clear the text if ID is blank
            } else {
                if (idExists) {
                    binding.idCheckText.text = "이미 사용 중인 아이디 입니다"
                    binding.idCheckText.setTextColor(Color.RED) // Set text color to red for duplicate ID
                    idCheck = false
                } else {
                    binding.idCheckText.text = "사용 가능한 아이디 입니다."
                    binding.idCheckText.setTextColor(Color.GREEN) // Set text color to green for non-duplicate ID
                    idCheck = true
                }
            }
        })

        binding.passwordToText.addTextChangedListener { editable ->
            val passwordCheck = editable.toString()
            val password = binding.passwordText.text.toString()
            if (binding.passwordToText.text.isBlank()) {
                binding.passwordCheckText.text = "" // Clear the text if ID is blank
            }
            if (passwordCheck == password) {
                binding.passwordCheckText.text = "비밀번호가 일치합니다."
                binding.passwordCheckText.setTextColor(Color.GREEN)
                pwCheck = true
            } else {
                binding.passwordCheckText.text = "비밀번호가 일치하지 않습니다."
                binding.passwordCheckText.setTextColor(Color.RED)
                pwCheck = false
            }
        }
    }
}