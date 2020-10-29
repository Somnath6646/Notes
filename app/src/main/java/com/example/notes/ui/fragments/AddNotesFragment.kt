package com.example.notes.ui.fragments

import android.hardware.input.InputManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.data.NotesViewModel
import com.example.notes.databinding.FragmentAddNotesBinding
import com.example.notes.ui.base.BaseFragment
import com.example.notes.utils.NavigationCallBacks

class AddNotesFragment : BaseFragment<FragmentAddNotesBinding, NotesViewModel>(), NavigationCallBacks {
    override fun getLayoutResource(): Int = R.layout.fragment_add_notes

    override fun getViewModel(): Class<NotesViewModel> = NotesViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.setNavigationCallBack(this)

    }

    override fun navigateToAddNotes() {

    }

    override fun navigateToNoteList() {

        findNavController().popBackStack()

    }


}