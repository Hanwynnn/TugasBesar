package com.willychia.myapplication.Room.RoomFilm

import androidx.room.*
import com.willychia.myapplication.Room.RoomFilm.NoteFilm

@Dao
interface NoteFilmDao {
    @Insert
    fun addNoteFilm(note: NoteFilm)

    @Update
    fun updateNoteFilm(note: NoteFilm)

    @Delete
    fun deleteNoteFilm(note: NoteFilm)

    @Query("SELECT * FROM NoteFilm")
    fun getNotesFilm() : List<NoteFilm>

    @Query("SELECT * FROM NoteFilm WHERE id =:note_id")
    fun getNote(note_id: Int) : List<NoteFilm>
}