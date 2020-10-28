package com.example.notes.data

import android.icu.text.CaseMap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes_List")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Notes_Id")
    var id: Int,
    @ColumnInfo(name = "Title")
    var title:String,

    @ColumnInfo(name = "Description")
    var description: String

)