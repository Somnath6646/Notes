package com.example.notes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.Notes
import com.example.notes.databinding.ListItemBinding
import com.example.notes.generated.callback.OnClickListener

class NoteListAdapter(private val clickListener: (Notes) -> Unit): RecyclerView.Adapter<NoteListViewHolder>(){

    private val notesList = ArrayList<Notes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(inflater, R.layout.list_item, parent , false)
        return NoteListViewHolder(binding = binding)
    }

    override fun getItemCount(): Int {
      return  notesList.size
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.bind(notesList[position], clickListener)
    }

    fun setList(notes:List<Notes>){
        notesList.clear()
        notesList.addAll(notes)
    }

}

class NoteListViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(notes: Notes, clickListener: (Notes) -> Unit){
          binding.title.text = notes.title
          binding.description.text = notes.description
          binding.listItemLayout.setOnClickListener{
              clickListener(notes)
          }
      }
}