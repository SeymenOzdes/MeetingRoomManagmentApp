package com.example.meetingroom.ui

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroom.R

class AddRoomActivity : AppCompatActivity() {
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_room)

        val datePickerButton: Button = findViewById(R.id.datePickerButton)
        val selectedDateText: TextView = findViewById(R.id.dateText)
        val spinner: Spinner = findViewById(R.id.meetingRoomsSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.meetingRooms,
            android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
        }
        datePickerButton.setOnClickListener {
            showDatePickerDialog { selectedDate ->
                datePickerButton.text = selectedDate
            }
        }
    }

    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        // Şu anki tarihi almak için Calendar kullanıyoruz.
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Seçilen tarihi formatlıyoruz (ay 0-indexed olduğu için +1 ekliyoruz).
            val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            onDateSelected(formattedDate)
        }, year, month, day)

        datePickerDialog.show()
    }
}
