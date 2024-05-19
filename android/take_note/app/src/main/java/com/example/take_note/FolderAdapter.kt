package com.example.take_note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FolderAdapter(val noteList: MutableList<Folder>) :
    RecyclerView.Adapter<FolderAdapter.NoteViewHolder>() {
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameFolder = itemView.findViewById<TextView>(R.id.name_of_folder)
        val totalNote = itemView.findViewById<TextView>(R.id.total_note)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_folder_to_do_list, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.nameFolder.text = noteList[position].nameFolder
        holder.totalNote.text = noteList[position].totalNote
    }
}