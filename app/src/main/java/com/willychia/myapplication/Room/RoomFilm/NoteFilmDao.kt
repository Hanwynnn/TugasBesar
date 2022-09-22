package com.willychia.myapplication.Room.RoomFilm

import androidx.room.*
import com.willychia.myapplication.Room.RoomFilm.NoteFilm

@Dao
interface NoteFilmDao {
    @Insert
    suspend fun addNoteFilm(note: NoteFilm)

    @Update
    suspend fun updateNoteFilm(note: NoteFilm)

    @Delete
    suspend fun deleteNoteFilm(note: NoteFilm)

    @Query("SELECT * FROM NoteFilm")
    suspend fun getNotesFilm() : List<NoteFilm>

    @Query("SELECT * FROM NoteFilm WHERE id =:note_id")
    suspend fun getNote(note_id: Int) : List<NoteFilm>
}