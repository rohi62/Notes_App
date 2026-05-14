package com.example.notes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Adapters.NotesAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var notesList: MutableList<String>
    lateinit var adapter: NotesAdapter
    lateinit var notebtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recylerview1)
        notebtn=findViewById(R.id.writes)

        notebtn.setOnClickListener {
            var intent= Intent(this, WritingActivity::class.java)
            startActivity(intent)
        }



        notesList = loadNotes()

        adapter = NotesAdapter(
            notesList,

            onItemClick = { position, noteText ->
                val intent = Intent(this, WritingActivity::class.java)
                intent.putExtra("note_text", noteText)
                intent.putExtra("note_index", position)
                startActivity(intent)
            },

            onDeleteClick = { position ->
                notesList.removeAt(position)
                saveNotes(notesList)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, notesList.size)
                Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


    private fun loadNotes(): MutableList<String> {
        val sharePref = getSharedPreferences("MyData", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharePref.getString("notes", "[]")
        val type = object : TypeToken<MutableList<String>>() {}.type
        return gson.fromJson(json, type)
    }


    private fun saveNotes(notes: MutableList<String>) {
        val sharePref = getSharedPreferences("MyData", Context.MODE_PRIVATE)
        val gson = Gson()
        sharePref.edit()
            .putString("notes", gson.toJson(notes))
            .apply()
    }
}


