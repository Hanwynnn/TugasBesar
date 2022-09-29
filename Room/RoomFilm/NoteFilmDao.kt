package com.willychia.TugasBesar.Room.RoomFilm

import androidx.room.*

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