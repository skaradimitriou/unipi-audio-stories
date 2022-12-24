package com.stathis.unipiaudiostories.models.repo

import com.google.firebase.database.FirebaseDatabase
import com.stathis.unipiaudiostories.models.data.StoryDto
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.mapper.StoryMapper
import com.stathis.unipiaudiostories.util.STORIES_DB_PATH
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class StoryRepositoryImpl() : StoryRepository {

    private val dbRef = FirebaseDatabase.getInstance().reference

    override suspend fun getAllStories(): Flow<List<Story>> = flow {
        val snapshot = dbRef.child(STORIES_DB_PATH).get().await()
        val list = snapshot.children.map { it.getValue(StoryDto::class.java) }
        val mappedList = StoryMapper.fromDataToDomainModel(list)
        emit(mappedList)
    }
}