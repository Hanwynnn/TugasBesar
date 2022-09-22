package com.willychia.myapplication.Room.RoomPengunjung

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotePengunjung (
    @PrimaryKey(autoGenerate = true)
    val idPengunjung: Int,
    val nama:String,
    val tglLahir: String,
    val email: String,
    val password: String,
    val noTelp: String
)