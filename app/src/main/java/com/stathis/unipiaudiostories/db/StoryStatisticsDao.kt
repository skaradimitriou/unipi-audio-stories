package com.stathis.unipiaudiostories.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stathis.unipiaudiostories.models.domain.StoryStatistic
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryStatisticsDao {

    /**
     * Query that returns all the statistics sorted by count property ASC
     */

    @Query("SELECT * FROM statistics ORDER BY counter ASC")
    fun getAllStatistics(): Flow<List<StoryStatistic>>

    /**
     * Query that inserts a list of StoryStatistic model
     */

    @Insert
    suspend fun insertStatistics(list: List<StoryStatistic>)

    /**
     * Query that deletes all the statistics from the local db.
     */

    @Query("DELETE FROM statistics")
    suspend fun deleteAll()
}