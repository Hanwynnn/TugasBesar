package com.willychia.myapplication.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.willychia.myapplication.Room.RoomFilm.NoteFilm
import com.willychia.myapplication.Room.RoomFilm.NoteFilmDao
import com.willychia.myapplication.Room.RoomPengunjung.NotePengunjung
import com.willychia.myapplication.Room.RoomPengunjung.NotePengunjungDao

@Database(
    entities = [NoteFilm::class, NotePengunjung::class],
    version = 1
)

abstract class BigDB : RoomDatabase() {
    abstract fun pengunjungDAO(): NotePengunjungDao
    abstract fun filmDAO(): NoteFilmDao

    companion object {
        @Volatile
        private var instance: BigDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BigDB::class.java,
            "BigDatabase.db"
        ).allowMainThreadQueries().build()
    }
}