package com.example.notes.ui


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.data.NotesDatabase
import com.example.notes.data.NotesRepository
import com.example.notes.data.NotesViewModel
import com.example.notes.data.NotesViewModelFactory
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.utils.SwipeDeletionCallBack
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: NotesViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao = NotesDatabase.getInstance(applicationContext).notesDAO
        val repository = NotesRepository(dao)
        val factory = NotesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        this.supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_action_bar)
//        supportActionBar?.elevation = 1F


    }



    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.bringBackToNormal()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.clearAll -> viewModel.clearAll()
        }
        return super.onOptionsItemSelected(item)
    }
}
