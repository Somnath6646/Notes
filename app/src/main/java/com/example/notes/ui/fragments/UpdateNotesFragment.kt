package com.example.notes.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notes.R
import com.example.notes.data.NotesViewModel
import com.example.notes.databinding.FragmentAddNotesBinding
import com.example.notes.ui.base.BaseFragment

class UpdateNotesFragment : BaseFragment<FragmentAddNotesBinding, NotesViewModel>() {
    override fun getLayoutResource(): Int = R.layout.fragment_add_notes

    override fun getViewModel(): Class<NotesViewModel> = NotesViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}