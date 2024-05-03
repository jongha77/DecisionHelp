package com.example.decisionhelp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.decisionhelp.Adapter.VoterAdapter
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.Model.VoterViewModel
import com.example.decisionhelp.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: VoterViewModel by viewModel()
    var deadline: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = VoterAdapter(emptyList(), object : VoterAdapter.OnItemClickListener {
            override fun onItemClick(voter: Voter, isdeadline: Boolean) {
                viewModel.setSelectedVoter(voter)
                deadline = isdeadline
            }
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.voters.observe(this, Observer{ voters ->
            adapter.updateData(voters)
        })

        viewModel.selectedVoter.observe(this, Observer { selectedVoter ->
            selectedVoter?.let {
                val intent = Intent(this, VoteActivity::class.java).apply {
                    putExtra("SELECTED_VOTER", selectedVoter)
                    putExtra("deadline", deadline)
                }
                startActivity(intent)
                viewModel.setSelectedVoter(null)
            }
        })

        viewModel.getVoters()

        binding.EditBtn.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        binding.MyPageBtn.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }
    }
}