package com.example.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDAO {

    @Insert
    suspend fun insert(notes: Notes)

    @Delete
    suspend fun delete(notes: Notes)

    @Update
    suspend fun update(notes: Notes)

    @Query("SELECT * FROM Notes_List ORDER BY Notes_Id DESC")
    fun getAllNotes(): LiveData<List<Notes>>

    @Query("DELETE FROM Notes_List")
    suspend fun deleteALLNotes()
}