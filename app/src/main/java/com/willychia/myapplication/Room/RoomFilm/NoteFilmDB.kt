package com.willychia.myapplication.Room.RoomFilm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NoteFilm::class],
    version = 1
)
abstract class NoteFilmDB: RoomDatabase() {
    abstract fun noteDao() : NoteFilmDao
    companion object {
        @Volatile private var instance : NoteFilmDB? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NoteFilmDB::class.java,
                "note12345.db"
            ).build()
    }
}