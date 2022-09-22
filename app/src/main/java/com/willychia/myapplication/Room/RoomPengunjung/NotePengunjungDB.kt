package com.willychia.myapplication.Room.RoomPengunjung

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.willychia.myapplication.Room.RoomFilm.NoteFilm

@Database(
    entities = [NoteFilm::class],
    version = 1
)
abstract class NotePengunjungDB: RoomDatabase() {
    abstract fun noteDao() : NotePengunjungDao
    companion object {
        @Volatile private var instance : NotePengunjungDB? = null
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
                NotePengunjungDB::class.java,
                "note12345.db"
            ).build()
    }
}