package com.example.decisionhelp.View

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.decisionhelp.Adapter.ItemAdapter
import com.example.decisionhelp.Adapter.VoterAdapter
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.Data.itemResultCheck
import com.example.decisionhelp.Model.VoterViewModel
import com.example.decisionhelp.databinding.ActivityMypageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MypageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMypageBinding
    private val viewModel: VoterViewModel by viewModel()
    private var isEx: Boolean = true
    private lateinit var adapter: VoterAdapter
    private lateinit var MyVoterViewAdapter: VoterAdapter
    var itemResultCheck: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        //initRecyclerView()
        observeViewModel()

        // Fetch the list of voters
        viewModel.getVoters()
        viewModel.itemResultCheck(getUserId().toString())

        binding.name.text = getUserId().toString() + "님"

        binding.LogoutBtn.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }
    }

    private fun getUserId(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)
    }

    private fun observeViewModel() {
        viewModel.selectedVoter.observe(this, Observer{ selectedVoter ->
            selectedVoter?.let {
                val intent = Intent(this, VoteActivity::class.java).apply {
                    putExtra("SELECTED_VOTER", selectedVoter)
                    putExtra("IS_EXPIRED", isEx)
                }
                startActivity(intent)
                viewModel.setSelectedVoter(null) // Reset selected voter after starting activity
            }
        })

        viewModel.itemResultCheck.observe(this, Observer { _itemResultCheck ->
            _itemResultCheck.forEach { result ->
                if (getUserId().toString() == result.id && !itemResultCheck.contains(result.VoterCode)) {
                    itemResultCheck.add(result.VoterCode)
                }

                // Process viewModel.voters data only after itemResultCheck processing is complete
                viewModel.voters.value?.let { voters ->
                    processVoters(voters)
                }
            }
        })
    }
    private fun setupRecyclerView() {
        adapter = VoterAdapter(emptyList(), object : VoterAdapter.OnItemClickListener {
            override fun onItemClick(voter: Voter, isExpired: Boolean) {
                viewModel.setSelectedVoter(voter)
                isEx = isExpired
            }
        })
        binding.MyPostingView.adapter = adapter
        binding.MyPostingView.layoutManager = LinearLayoutManager(this)

        // Initialize MyVoterViewAdapter before setting it to the RecyclerView
        MyVoterViewAdapter = VoterAdapter(emptyList(), object : VoterAdapter.OnItemClickListener {
            override fun onItemClick(voter: Voter, isExpired: Boolean) {
                viewModel.setSelectedVoter(voter)
                isEx = isExpired
            }
        })
        binding.MyVoterView.adapter = MyVoterViewAdapter
        binding.MyVoterView.layoutManager = LinearLayoutManager(this)
    }
    private fun processVoters(voters: List<Voter>) {
        val userVoters = voters.filter { getUserId().toString() == it.id }
        adapter.updateData(userVoters)

        val MyVoters = voters.filter { voter ->
            itemResultCheck.contains(voter.voterCode)
        }
        MyVoterViewAdapter.updateData(MyVoters)
    }
}