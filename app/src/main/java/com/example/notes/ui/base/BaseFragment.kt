package com.example.notes.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.notes.data.NotesDatabase
import com.example.notes.data.NotesRepository
import com.example.notes.data.NotesViewModel
import com.example.notes.data.NotesViewModelFactory

abstract class BaseFragment<T: ViewDataBinding, VM: ViewModel>: Fragment(){

    protected lateinit var binding: T
    protected lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            getLayoutResource(),
            container,
            false
        )
        val dao = NotesDatabase.getInstance(requireActivity().applicationContext).notesDAO
        val repository = NotesRepository(dao)
        val factory = NotesViewModelFactory(repository)
        viewModel = ViewModelProvider(requireActivity(), factory).get(getViewModel())
        return binding.root
    }

    abstract fun getLayoutResource(): Int

    abstract fun getViewModel(): Class<VM>
}