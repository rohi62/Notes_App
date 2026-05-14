package com.example.notes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.Proxy

class WritingActivity : AppCompatActivity() {

    lateinit var edittext: EditText
    lateinit var savebtn: AppCompatButton
    private var noteIndex = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_writing)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        edittext = findViewById(R.id.edittext1)
        savebtn = findViewById(R.id.save)


        noteIndex = intent.getIntExtra("note_index", -1)
        val noteText = intent.getStringExtra("note_text") ?: ""
        edittext.setText(noteText)

        savebtn.setOnClickListener {
            val notedata = edittext.text.toString()

            if (notedata.isEmpty()) {
                startActivity(Intent(this, MainActivity::class.java))
                return@setOnClickListener
            }else {

                val sharePref = getSharedPreferences("MyData", Context.MODE_PRIVATE)
                val gson = Gson()
                val existingJson = sharePref.getString("notes", "[]")
                val type = object : TypeToken<MutableList<String>>() {}.type
                val notesList: MutableList<String> = gson.fromJson(existingJson, type)


                if (noteIndex != -1) {
                    notesList[noteIndex] = notedata
                } else {
                    notesList.add(notedata)
                }

                sharePref.edit()
                    .putString("notes", gson.toJson(notesList))
                    .apply()

                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}