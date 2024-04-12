package com.example.decisionhelp.Adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.decisionhelp.R

class EditAdapter(private val itemList: MutableList<String>, private val itemChangeListener: ItemChangeListener) : RecyclerView.Adapter<EditAdapter.ViewHolder>() {

    interface ItemChangeListener {
        fun onItemChanged(updatedItemList: MutableList<String>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_edit_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val themeEditText: EditText = itemView.findViewById(R.id.theme)
        private val deleteBtn: Button = itemView.findViewById(R.id.DeleteBtn)

        fun bind(item: String) {
            themeEditText.setText(item)

            themeEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Not used, but required by the interface
                }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    // Update the corresponding item in the itemList when text changes
                    val position = adapterPosition
                    itemList[position] = s.toString()
                    Log.d("ViewHolder", "Position: $position, Updated Item: ${itemList[position]}")
                    itemChangeListener.onItemChanged(itemList)
                }
            })

            deleteBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Remove the item from the list
                    itemList.removeAt(position)
                    // Notify the adapter that the item has been removed
                    notifyItemRemoved(position)
                    itemChangeListener.onItemChanged(itemList)
                }
            }
        }
    }
}
