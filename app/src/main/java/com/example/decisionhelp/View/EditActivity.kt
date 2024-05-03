package com.example.decisionhelp.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.decisionhelp.Adapter.EditAdapter
import com.example.decisionhelp.Model.VoterViewModel
import com.example.decisionhelp.R
import com.example.decisionhelp.databinding.ActivityEditBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class EditActivity : AppCompatActivity(), EditAdapter.ItemChangeListener {
    private lateinit var binding: ActivityEditBinding
    private val viewModel: VoterViewModel by viewModel()
    var Checked: Boolean = false
    private var calendar = Calendar.getInstance()
    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var day = calendar.get(Calendar.DAY_OF_MONTH)
    private lateinit var adapter: EditAdapter
    private var itemList: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val radioGroup = binding.radioGroup
        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = EditAdapter(itemList, this)
        recyclerView.adapter = adapter

        binding.addBtn.setOnClickListener {
            addItemToList()
        }
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val isChecked = when (checkedId) {
                R.id.rg_btn1 -> true // 중복 허용 선택
                R.id.rg_btn2 -> false // 중복 미허용 선택
                else -> false // 디폴트 값 설정 (여기서는 중복 미허용으로 설정)
            }
            Checked = isChecked
        }

        binding.CalendarBtn.text = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
        binding.TimerBtn.text = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)

        binding.CalendarBtn.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, day ->
                val formattedMonth = if (month + 1 < 10) "0${month + 1}" else "${month + 1}"
                val formattedDay = if (day < 10) "0$day" else "$day"
                binding.CalendarBtn.text = "$year-$formattedMonth-$formattedDay"
            }, year, month, day)
            datePickerDialog.show()
        }

        binding.TimerBtn.setOnClickListener {
            val cal = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.TimerBtn.text = SimpleDateFormat("HH:mm").format(cal.time)
            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        binding.completeBtn.setOnClickListener {
            val voterTitle = binding.title.text.toString()
            val voterDetail = binding.detail.text.toString()
            val voterDate = binding.CalendarBtn.text.toString()
            val voterTime =  binding.TimerBtn.text.toString()
            val voterWhether = Checked
            val id =  getUserId().toString()
            val combinedString = "$voterTitle-$voterTime-$id"
            val encodedString = Base64.encodeToString(combinedString.toByteArray(), Base64.DEFAULT)
            val voterCode = encodedString

            if(voterTitle != "" || voterDetail != "" || itemList.size >= 2) {
                viewModel.voter(voterCode, voterTitle, voterDetail, voterDate, voterTime, voterWhether, id)
                itemList.forEachIndexed { index, item ->
                    viewModel.voterTheme(voterCode, item) }
            }else
                Toast.makeText(this, "투표 게시 실패ㅠ", Toast.LENGTH_SHORT).show()

        }

        viewModel.voterResult.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "투표 게시 성공!", Toast.LENGTH_SHORT).show()
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        })

    }
    private fun getUserId(): String? {
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)
    }

    override fun onItemChanged(updatedItemList: MutableList<String>) {
        itemList = updatedItemList
    }

    private fun addItemToList() {
        val newItem = ""
        itemList.add(newItem)
        adapter.notifyItemInserted(itemList.size - 1)
    }
}