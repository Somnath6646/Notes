package com.example.notes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class],version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase(){
    abstract val notesDAO: NotesDAO

    companion object{
        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase:: class.java,
                        "Notes_Database"
                    ).build()
                }
                return instance
            }
        }
    }
}