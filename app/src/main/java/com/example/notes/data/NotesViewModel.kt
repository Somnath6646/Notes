package com.example.notes.data

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.utils.NavigationCallBacks
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NotesRepository): ViewModel(), Observable{

    val noteList = repository.notesList

    private var isSave = true

    private lateinit var notes: Notes

    private lateinit var navigationCallBacks: NavigationCallBacks

    fun setNavigationCallBack(navigationCallBacks: NavigationCallBacks){
        this.navigationCallBacks = navigationCallBacks
    }

    @Bindable
    var title = MutableLiveData<String>()

    @Bindable
    var description = MutableLiveData<String>()

    @Bindable
    var saveOrUpdateText = MutableLiveData<String>()

    init {
        saveOrUpdateText.value = "Save"
    }

    fun setTitleAndDescription(notes: Notes){
        title.value = notes.title
        description.value = notes.description
        isSave = false
        this.notes = notes
        saveOrUpdateText.value = "Update"
    }


    fun bringBackToNormal(){
        title.value = null
        description.value = null
        isSave = true
        saveOrUpdateText.value = "Save"
    }


    fun saveNote(){
        viewModelScope.launch {
            if(title.value != null) {
                if (description.value == null) description.value = ""
            }
            if(description.value != null){
                if(title.value == null) title.value = ""
        }
            if (title.value != null && description.value != null) {

                if (isSave) {
                    insert(Notes(title = title.value!!, description = description.value!!, id = 0))
                } else {
                    notes.title = title.value!!
                    notes.description = description.value!!
                    update(notes = notes)
                }
                bringBackToNormal()
                navigationCallBacks.navigateToNoteList()
            }
        }

    }

     fun insert(notes: Notes){
         viewModelScope.launch {
             repository.insert(notes)
             title.value = null
             description.value = null
         }

    }

    fun update(notes: Notes){
        viewModelScope.launch {
            repository.update(notes)
            title.value = null
            description.value = null
        }
    }

    fun clearAll(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun delete(notes: Notes){
        viewModelScope.launch {
            repository.delete(notes)
        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}

