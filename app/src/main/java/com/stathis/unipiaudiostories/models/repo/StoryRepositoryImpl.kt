package com.stathis.unipiaudiostories.models.repo

import com.google.firebase.database.DatabaseReference
import com.stathis.unipiaudiostories.db.StoriesDao
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
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementation class of [StoryRepository]'s methods.
 */

class StoryRepositoryImpl @Inject constructor(
    private val dbRef: DatabaseReference,
    private val localDb: StoriesDao,
    private val authenticator: Authenticator
) : StoryRepository {

    /**
     * The app caches the List<Story> inside the Local db before it returns the data to the
     * ViewModel.
     */

    override suspend fun getAllStories(): Flow<List<Story>> = flow {
        val cachedList = localDb.getAllCountries()
        val dbSnapshot = dbRef.child(STORIES_DB_PATH).get().await()
        val list = dbSnapshot.children.map { it.getValue(StoryDto::class.java) }
        val mappedList = StoryMapper.fromDataToDomainModel(list)

        if (!list.isNullOrEmpty()) {
            localDb.deleteAll()
            localDb.insertStories(mappedList)
            val newCachedList = localDb.getAllCountries()
            emitAll(newCachedList)
        } else {
            emitAll(cachedList)
        }
    }

    override suspend fun getStoryStatistics(): Flow<List<StoryStatistic>> = flow {
        authenticator.getActiveUser()?.uid?.let { userId ->
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