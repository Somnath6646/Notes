package com.example.notes.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.Notes
import com.example.notes.data.NotesViewModel
import com.example.notes.databinding.FragmentAddNotesBinding
import com.example.notes.databinding.FragmentNotesListBinding
import com.example.notes.ui.MainActivity
import com.example.notes.ui.NoteListAdapter
import com.example.notes.ui.base.BaseFragment
import com.example.notes.utils.NavigationCallBacks

class NotesListFragment : BaseFragment<FragmentNotesListBinding, NotesViewModel>() {
    override fun getLayoutResource(): Int = R.layout.fragment_notes_list

    override fun getViewModel(): Class<NotesViewModel> = NotesViewModel::class.java


    private lateinit var adapter: NoteListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.floatingActionButton.setOnClickListener{
            navigateToAddNotesFragment()
        }
        binding.viewModel = viewModel
        initRecyclerView()
    }

    fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = NoteListAdapter({note: Notes -> listItemClicked(note)})
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        displayNotes()
    }

    fun displayNotes(){
        viewModel.noteList.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    fun navigateToAddNotesFragment(){
        val action = NotesListFragmentDirections.actionNotesListFragmentToAddNotesFragment()
        findNavController().navigate(action)
    }

    fun listItemClicked(notes: Notes){
        viewModel.setTitleAndDescription(notes)
        navigateToAddNotesFragment()
    }



}