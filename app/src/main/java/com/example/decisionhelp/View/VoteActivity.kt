package com.example.decisionhelp.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.databinding.ActivityVoteBinding

class VoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the Parcelable object from the Intent
        val selectedVoter = intent.getParcelableExtra<Voter>("SELECTED_VOTER")

        // Check if the received object is not null
        if (selectedVoter != null) {
            // Access the properties of the Parcelable object
            val voterTitle = selectedVoter.voterDetail
            binding.detailText.text = voterTitle
        } else {
            // Handle the case where the object is null
        }
    }
}