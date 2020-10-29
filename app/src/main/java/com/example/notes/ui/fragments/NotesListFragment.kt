package com.example.notes.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
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
import com.example.notes.utils.SwipeDeletionCallBack
import com.example.notes.utils.SwipeToShareCallback
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_notes_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        enableSwipeToDeleteAndUndo()
        enableSwipeToShare()
    }

    fun initRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = NoteListAdapter({note: Notes -> listItemClicked(note)})
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        displayNotes()
    }


    fun enableSwipeToDeleteAndUndo(){

        val swipeDeletionCallBack = object : SwipeDeletionCallBack(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = adapter.notes[position]

                viewModel.delete(note)
                adapter.notifyDataSetChanged()
                Snackbar.make(constraintLayout, "Note Deleted", Snackbar.LENGTH_SHORT).setAction("Undo", View.OnClickListener {


                    viewModel.insert(note)
                    adapter.notifyDataSetChanged()
                    binding.recyclerView.scrollToPosition(position)
                }).setActionTextColor(resources.getColor(R.color.colorAccent)).show()

            }
        }

        var itemTouchHelper = ItemTouchHelper(swipeDeletionCallBack)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }


    fun enableSwipeToShare(){

        var itemTouchHelper: ItemTouchHelper? = null
        val swipeToShareCallback = object : SwipeToShareCallback(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = adapter.notes[position]

                itemTouchHelper!!.attachToRecyclerView(null)
                itemTouchHelper!!.attachToRecyclerView(binding.recyclerView)
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, note.title+"\n"+note.description)
                val shareIntent = Intent.createChooser(intent, "Share via")
                startActivity(shareIntent)

            }
        }

        itemTouchHelper = ItemTouchHelper(swipeToShareCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    fun displayNotes(){
        viewModel.noteList.observe(viewLifecycleOwner, Observer {
            println(it.toString())
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