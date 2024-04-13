package com.example.decisionhelp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.decisionhelp.Data.Voter
import com.example.decisionhelp.R

class VoterAdapter(
    private var voters: List<Voter>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<VoterAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(voter: Voter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voter = voters[position]
        holder.bind(voter.voterTitle)
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
                listener.onItemClick(voter)
            }
        }

        fun bind(title: String) {
            titleTextView.text = title
        }
    }

    fun updateData(newData: List<Voter>) {
        voters = newData
        notifyDataSetChanged()
    }
}