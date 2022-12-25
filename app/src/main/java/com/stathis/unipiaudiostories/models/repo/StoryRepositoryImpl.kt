package com.stathis.unipiaudiostories.models.repo

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.stathis.unipiaudiostories.db.StoriesDatabase
import com.stathis.unipiaudiostories.models.data.StoryDto
import com.stathis.unipiaudiostories.models.data.StoryStatisticDto
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.domain.StoryStatistic
import com.stathis.unipiaudiostories.models.mapper.StoryMapper
import com.stathis.unipiaudiostories.models.mapper.StoryStatisticMapper
import com.stathis.unipiaudiostories.util.STORIES_DB_PATH
import com.stathis.unipiaudiostories.util.USERS_DB_PATH
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class StoryRepositoryImpl(context: Context) : StoryRepository {

    private val dbRef = FirebaseDatabase.getInstance().reference
    private val localDb = StoriesDatabase.getDatabase(context).countriesDao()

    override suspend fun getAllStories(): Flow<List<Story>> = flow {
        val cachedList = localDb.getAllCountries().first()

        val snapshot = dbRef.child(STORIES_DB_PATH).get().await()
        val list = snapshot.children.map { it.getValue(StoryDto::class.java) }
        val mappedList = StoryMapper.fromDataToDomainModel(list)

        if (!list.isNullOrEmpty()) {
            localDb.deleteAll()
            localDb.insertStories(mappedList)
            val newCachedList = localDb.getAllCountries().first()
            emit(newCachedList)
        } else {
            emit(cachedList)
        }
    }

    override suspend fun getStoryStatistics(): Flow<List<StoryStatistic>> = flow {
        Authenticator.getActiveUser()?.uid?.let { userId ->
            val snapshot = dbRef.child(USERS_DB_PATH).child(userId).get().await()
            val list = snapshot.children
                .map { it.getValue(StoryStatisticDto::class.java) }
                .sortedByDescending { it?.counter }

            val mappedList = StoryStatisticMapper.fromDataToDomainModel(list)
            emit(mappedList)
        } ?: kotlin.run {
            emit(listOf())
        }
    }
}