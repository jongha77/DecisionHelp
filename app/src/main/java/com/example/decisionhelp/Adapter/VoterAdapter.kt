package com.example.decisionhelp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.R
import java.text.SimpleDateFormat
import java.util.*

class VoterAdapter(
    private var voters: List<Voter>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<VoterAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(voter: Voter, isExpired: Boolean)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voter = voters[position]
        holder.bind(voter)
    }

    override fun getItemCount(): Int {
        return voters.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val titleTextView: TextView = itemView.findViewById(R.id.title)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val voter = voters[position]
                val isExpired = isExpired(voter)
                listener.onItemClick(voter, isExpired)
            }
        }
        fun bind(voter: Voter) {
            titleTextView.text = voter.voterTitle

            // 상태에 따라 색상 변경
            if (isExpired(voter))
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.gray))

        }

        private fun isExpired(voter: Voter): Boolean {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val currentDate = Calendar.getInstance().time
            val dateTimeString = "${voter.voterDate} ${voter.voterTime}"
            val date: Date = sdf.parse(dateTimeString) ?: currentDate
            val isExpired = date.before(currentDate)

            return isExpired
        }
    }

    fun updateData(newData: List<Voter>) {
        voters = newData
        notifyDataSetChanged()
    }
}