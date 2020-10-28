package com.example.notes.data

class NotesRepository (private val dao: NotesDAO){

    val notesList = dao.getAllNotes()

    suspend fun insert(notes: Notes){
        dao.insert(notes)
    }

    suspend fun delete(notes: Notes){
        dao.delete(notes)
    }

    suspend fun update(notes: Notes){
        dao.update(notes)
    }

    suspend fun deleteAll(){
        dao.deleteALLNotes()
    }
}