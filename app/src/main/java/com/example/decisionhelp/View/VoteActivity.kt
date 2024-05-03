package com.example.decisionhelp.View

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.decisionhelp.Adapter.ItemAdapter
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.Model.VoterViewModel
import com.example.decisionhelp.databinding.ActivityVoteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class VoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVoteBinding
    private val viewModel: VoterViewModel by viewModel()
    private lateinit var itemAdapter: ItemAdapter
    private var deadline: Boolean = false
    private var voterCode: String = ""
    private var Date: String =""
    private var Time: String =""
    private var id: String =""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        val selectedVoter = intent.getParcelableExtra<Voter>("SELECTED_VOTER")
        deadline = intent.getBooleanExtra("deadline", false)

        if (selectedVoter != null) {
            val voterTitle = selectedVoter.voterTitle
            val voterDetail = selectedVoter.voterDetail
            voterCode = selectedVoter.voterCode
            Date = selectedVoter.voterDate
            Time = selectedVoter.voterTime
            id = selectedVoter.id
            binding.titleText.text = voterTitle
            binding.detailText.text = voterDetail
            itemAdapter.voterWhether = selectedVoter.voterWhether
            itemAdapter.id = getUserId().toString()
        }
        viewModel.votersItem.observe(this, Observer { voterItems ->
            itemAdapter.submitList(voterItems)
        })

        viewModel.itemResultCheck.observe(this, Observer { _itemResultCheck ->
            itemAdapter.itemResultCheck = _itemResultCheck.toMutableList()
        })

        viewModel.getVoterItems(voterCode)
        viewModel.itemResultCheck(getUserId().toString(),voterCode)


        if(id == itemAdapter.id){
            binding.completeBtn.text = "투표 마감"
            itemAdapter.setClickListenerEnabled(false)
            binding.completeBtn.text = "마감 하기"

            binding.completeBtn.setOnClickListener(){
                val currentDateAndTime = LocalDateTime.now()
                val date = currentDateAndTime.toLocalDate().toString()
                val time = currentDateAndTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                viewModel.voterClosed(voterCode,date,time)
                Toast.makeText(this, "마감 되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }else if(hasPassed(convertToDateTime(Date, Time))){
            itemAdapter.setClickListenerEnabled(false)
            binding.completeBtn.text = "투표 마감"
            binding.completeBtn.isEnabled = false
        } else {
            binding.completeBtn.setOnClickListener() {
                val voterResults = itemAdapter.voterResults
                for (result in voterResults) {
                    viewModel.itemResult(result.voterCode,result.voterItemCode, result.id, result.result)
                    viewModel.voterCount(result.voterItemCode, result.count)
                }
                Toast.makeText(this, "투표 완료!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@VoteActivity)
            itemAdapter = ItemAdapter()
            adapter = itemAdapter
        }
    }

    private fun getUserId(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToDateTime(dateString: String, timeString: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm") // Adjust pattern as per your date/time format
        val dateTimeString = "$dateString $timeString"
        return LocalDateTime.parse(dateTimeString, formatter)
    }

    // Function to check if savedDateTime has passed compared to current time
    @RequiresApi(Build.VERSION_CODES.O)
    private fun hasPassed(savedDateTime: LocalDateTime): Boolean {
        val currentDateTime = LocalDateTime.now()
        return currentDateTime.isAfter(savedDateTime)
    }
}