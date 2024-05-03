package com.example.decisionhelp.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.decisionhelp.Data.VoterItem
import com.example.decisionhelp.Data.VoterResult
import com.example.decisionhelp.Data.itemResultCheck
import com.example.decisionhelp.R

class ItemAdapter : ListAdapter<VoterItem, ItemAdapter.VoterViewHolder>(VoterDiffCallback()) {
    var voterWhether: Int = 2
    var id: String = ""
    var voterResults: MutableList<VoterResult> = mutableListOf()
    var itemResultCheck: MutableList<itemResultCheck> = mutableListOf()
    private var voterCode: String = ""
    // Variable to control whether the click listener is enabled or disabled
    private var isClickListenerEnabled: Boolean = true

    // Method to enable or disable the click listener
    fun setClickListenerEnabled(enabled: Boolean) {
        isClickListenerEnabled = enabled
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_voter_list, parent, false)
        return VoterViewHolder(view)
    }

    override fun onBindViewHolder(holder: VoterViewHolder, position: Int) {
        val voterItem = getItem(position)
        holder.bind(voterItem)
    }


    inner class VoterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val voterItemDetailTextView: TextView = itemView.findViewById(R.id.theme)
        private val voterItemCountTextView: TextView = itemView.findViewById(R.id.itemCount)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        private var voterItemCode: Int = 0
        private var currentItem: VoterItem? = null

        init {
            if (!isClickListenerEnabled) {
                // 본인이 게시한 튜표이거나 시간이 만료됨
                checkBox.setBackgroundResource(R.drawable.checkbox_gray)
            } else {
                checkBox.setBackgroundResource(R.drawable.selector)
            }

            checkBox.setOnClickListener {
                if (isClickListenerEnabled) {
                    currentItem?.let { item ->
                        if (voterWhether == 1) {
                            // Uncheck all items except the clicked one
                            currentList.forEach { it.isChecked = it == item }
                            notifyDataSetChanged()
                        } else if (voterWhether == 0) {
                            // Toggle the checked state
                            item.isChecked = !item.isChecked
                        }

                        // Update the count based on the checkbox state
                        if (checkBox.isChecked) {
                            item.count++
                        } else {
                            item.count--
                        }

                        // Update voterItemCountTextView regardless of the condition
                        voterItemCountTextView.text = item.count.toString()

                        // Update or add the result to voterResults
                        val existingResultIndex = voterResults.indexOfFirst { it.voterItemCode == item.voterItemCode }
                        if (existingResultIndex != -1) {
                            val existingResult = voterResults[existingResultIndex]
                            existingResult.count = item.count
                            existingResult.result = item.isChecked
                        } else {
                            voterResults.add(VoterResult(voterCode,item.voterItemCode, id, item.count, item.isChecked))
                        }
                    }
                }
            }
        }

        fun bind(voterItem: VoterItem) {
            currentItem = voterItem
            voterItemDetailTextView.text = voterItem.voterItemDetail
            voterItemCountTextView.text = voterItem.count.toString()
            voterItemCode = voterItem.voterItemCode
            voterCode = voterItem.voterCode
            // Set checkbox state
            itemResultCheck.forEach { result ->
                if (voterItem.voterItemCode == result.voterItemCode) {
                    checkBox.isChecked = result.result != 0
                    // If the checkbox state is set and no further condition checks are needed,
                    // you can exit the forEach loop
                    return@forEach
                }
            }
        }
    }

    class VoterDiffCallback : DiffUtil.ItemCallback<VoterItem>() {
        override fun areItemsTheSame(oldItem: VoterItem, newItem: VoterItem): Boolean {
            return oldItem.voterItemCode == newItem.voterItemCode
        }

        override fun areContentsTheSame(oldItem: VoterItem, newItem: VoterItem): Boolean {
            return oldItem == newItem
        }
    }
}