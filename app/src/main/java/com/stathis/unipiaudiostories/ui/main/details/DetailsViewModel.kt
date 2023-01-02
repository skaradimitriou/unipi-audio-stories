package com.stathis.unipiaudiostories.ui.main.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.stathis.unipiaudiostories.models.data.StoryStatisticDto
import com.stathis.unipiaudiostories.models.domain.StoryStatistic
import com.stathis.unipiaudiostories.models.mapper.StoryStatisticMapper
import com.stathis.unipiaudiostories.util.USERS_DB_PATH
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val authenticator: Authenticator,
    private val dbRef: DatabaseReference
) : ViewModel() {

    fun incrementCounterOnDb(storyName: String) {
        val uuid = authenticator.getActiveUser()?.uid.toString()
        viewModelScope.launch(Dispatchers.IO) {
            val snapshot = dbRef.child(USERS_DB_PATH).child(uuid).get().await()
            val list = snapshot.children.map { it.getValue(StoryStatisticDto::class.java) }
            val mappedList = StoryStatisticMapper.fromDataToDomainModel(list)

            val existsInDb = mappedList.any { it.storyName == storyName }
            if (existsInDb) {
                //increment counter for the item that matches the criteria
                val mutableList = mappedList.toMutableList().apply {
                    find { it.storyName == storyName }?.apply {
                        counter += 1L
                    }
                }
                // update list in realtime db
                dbRef.child(USERS_DB_PATH).child(uuid).setValue(mutableList).await()
            } else {
                val newList = mappedList.plus(StoryStatistic(null, storyName, 1))
                dbRef.child(USERS_DB_PATH).child(uuid).setValue(newList).await()
            }
        }
    }
}