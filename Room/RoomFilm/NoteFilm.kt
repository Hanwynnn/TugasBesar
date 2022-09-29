package com.willychia.TugasBesar.Room.RoomFilm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteFilm (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val judul: String,
    val genre: String,
    val rating: Float
)