package com.example.notes.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.notes.R
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private val notes: List<String>,
                   private val onItemClick: (Int, String) -> Unit,
    private val onDeleteClick: (Int) -> Unit):
        RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val frontviews: TextView = view.findViewById(R.id.frontview)
        val deletebtn: TextView=view.findViewById(R.id.deletedata)
    }

    override fun onCreateViewHolder(p0: ViewGroup, ViewGroup: Int): NoteViewHolder {
        val view = LayoutInflater.from(p0.context)
            .inflate(R.layout.recyclerview, p0, false)
        return NoteViewHolder(view)

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.frontviews.text = notes[position]

        holder.itemView.setOnClickListener {
            onItemClick(position,notes[position])
        }

        holder.deletebtn.setOnClickListener {
            onDeleteClick(position)
        }

    }

    override fun getItemCount()= notes.size



        }