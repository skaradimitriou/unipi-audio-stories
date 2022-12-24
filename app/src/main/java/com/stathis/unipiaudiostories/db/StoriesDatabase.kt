package com.stathis.unipiaudiostories.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stathis.unipiaudiostories.models.domain.Story

@Database(entities = arrayOf(Story::class), version = 1, exportSchema = false)
abstract class StoriesDatabase : RoomDatabase() {

    abstract fun countriesDao(): StoriesDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StoriesDatabase? = null

        fun getDatabase(context: Context): StoriesDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoriesDatabase::class.java,
                    "stories_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}