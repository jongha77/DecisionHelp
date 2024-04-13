package com.example.decisionhelp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.decisionhelp.Adapter.VoterAdapter
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.Model.VoterViewModel
import com.example.decisionhelp.databinding.ActivityHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: VoterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView and adapter
        val adapter = VoterAdapter(emptyList(), object : VoterAdapter.OnItemClickListener {
            override fun onItemClick(voter: Voter) {
                viewModel.setSelectedVoter(voter)
            }
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe the list of voters
        viewModel.voters.observe(this, { voters ->
            adapter.updateData(voters)
        })

        viewModel.selectedVoter.observe(this, { selectedVoter ->
            selectedVoter?.let {
                val intent = Intent(this, VoteActivity::class.java).apply {
                    putExtra("SELECTED_VOTER", selectedVoter)
                }
                startActivity(intent)
                viewModel.setSelectedVoter(null) // Reset selected voter after starting activity
            }
        })

        // Fetch the list of voters
        viewModel.getVoters()

        // Handle button clicks
        binding.EditBtn.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        binding.MyPageBtn.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }
    }
}