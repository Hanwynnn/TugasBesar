package com.willychia.TugasBesar.Room.RoomPengunjung

import androidx.room.*

@Dao
interface NotePengunjungDao {
    @Insert
    fun addNotePengunjung(note: NotePengunjung)

    @Update
    fun updateNotePengunjung(note: NotePengunjung)

    @Query("SELECT * FROM NotePengunjung")
    fun getNotesPengunjung(): List<NotePengunjung>

    @Query("SELECT * FROM notePengunjung WHERE idPengunjung=:note_id")
    fun getPengunjung(note_id: Int): List<NotePengunjung>
}