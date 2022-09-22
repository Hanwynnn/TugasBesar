package com.willychia.myapplication.Room.RoomPengunjung

import androidx.room.*
import com.willychia.myapplication.Room.RoomPengunjung.NotePengunjung

@Dao
interface NotePengunjungDao {
    @Insert
    fun addNotePengunjung(note: NotePengunjung)

    @Update
    fun updateNotePengunjung(note: NotePengunjung)

    @Delete
    fun deleteNotePengunjung(note: NotePengunjung)

    @Query("SELECT * FROM notePengunjung")
    fun getNotesPengunjung(): List<NotePengunjung>

    @Query("SELECT * FROM notePengunjung WHERE idPengunjung =:note_id")
    fun getPengunjung(note_id: Int): List<NotePengunjung>
}