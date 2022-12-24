package com.stathis.unipiaudiostories.models.repo

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.stathis.unipiaudiostories.db.StoriesDatabase
import com.stathis.unipiaudiostories.models.data.StoryDto
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.mapper.StoryMapper
import com.stathis.unipiaudiostories.util.STORIES_DB_PATH
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
}