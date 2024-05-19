package com.example.take_note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "folder")
data class Folder(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo("name_folder")
    val nameFolder: String,
    @ColumnInfo("total_note")
    val totalNote: String
)
