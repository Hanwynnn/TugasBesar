package com.willychia.myapplication.Room.RoomPengunjung

import androidx.room.*
import com.willychia.myapplication.Room.RoomPengunjung.NotePengunjung

@Dao
interface NotePengunjungDao {
    @Insert
    suspend fun addNotePengunjung(note: NotePengunjung)

    @Update
    suspend fun updateNotePengunjung(note: NotePengunjung)

    @Delete
    suspend fun deleteNotePengunjung(note: NotePengunjung)

    @Query("SELECT * FROM notePengunjung")
    suspend fun getNotesPengunjung(): List<NotePengunjung>

    @Query("SELECT * FROM notePengunjung WHERE idPengunjung =:note_id")
    suspend fun getPengunjung(note_id: Int): List<NotePengunjung>
}