package com.stathis.unipiaudiostories.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.domain.StoryStatistic

@Database(
    entities = arrayOf(Story::class, StoryStatistic::class),
    version = 2,
    exportSchema = false
)
abstract class StoriesDatabase : RoomDatabase() {

    abstract fun countriesDao(): StoriesDao
    abstract fun statisticsDao(): StoryStatisticsDao

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
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private const val DB_NAME = "stories_db"
    }
}