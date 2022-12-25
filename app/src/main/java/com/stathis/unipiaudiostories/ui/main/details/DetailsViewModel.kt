package com.stathis.unipiaudiostories.ui.main.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.stathis.unipiaudiostories.models.data.StoryStatisticDto
import com.stathis.unipiaudiostories.models.mapper.StoryStatisticMapper
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class DetailsViewModel : ViewModel() {

    private val dbRef = FirebaseDatabase.getInstance().reference

    fun incrementCounterOnDb(storyName: String) {
        val uuid = Authenticator.getActiveUser()?.uid.toString()
        viewModelScope.launch(Dispatchers.IO) {
//            val snapshot = dbRef.child("users").child(uuid).child(storyName).get().await()
//            val list = snapshot.children.map { it.getValue(StoryStatisticDto::class.java) }
//            val mappedList = StoryStatisticMapper.fromDataToDomainModel(list)
//            val storyStatistic = mappedList.find { it.storyName == storyName }
//            storyStatistic?.let {
//                it.counter++
//                dbRef.child("users").child(uuid).child(storyName).setValue(it).await()
//            }
        }
    }
}