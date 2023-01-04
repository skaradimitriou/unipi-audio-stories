package com.stathis.unipiaudiostories.ui.main.details

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.models.data.StoryDto
import com.stathis.unipiaudiostories.models.data.StoryStatisticDto
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.domain.StoryStatistic
import com.stathis.unipiaudiostories.models.mapper.StoryMapper
import com.stathis.unipiaudiostories.models.mapper.StoryStatisticMapper
import com.stathis.unipiaudiostories.util.FAVORITES_DB_PATH
import com.stathis.unipiaudiostories.util.USERS_DB_PATH
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    app: Application,
    private val authenticator: Authenticator,
    private val dbRef: DatabaseReference
) : BaseViewModel(app), FavoritesUtil {

    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _isFavorite = MutableLiveData(false)

    private val _favorites = MutableLiveData<List<Story>>(listOf())

    private var _tempFavoriteList = mutableListOf<Story>()

    private var _favoriteState = false
    var currentStory: Story? = null

    init {
        getUserFavorites()
    }

    fun observe(owner: LifecycleOwner) {
        _favorites.observe(owner) {
            val existsOrNot = _tempFavoriteList.any { it.title == currentStory?.title }
            _favoriteState = existsOrNot
            _isFavorite.postValue(existsOrNot)
        }
    }

    fun favoriteIconClicked() {
        if (_favoriteState) {
            deleteFromFavorites()
        } else {
            addToFavorites()
        }
    }

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

    override fun addToFavorites() {
        val uuid = authenticator.getActiveUser()?.uid.toString()
        viewModelScope.launch(Dispatchers.IO) {
            currentStory?.let {
                _tempFavoriteList.add(it)
            }

            dbRef.child(FAVORITES_DB_PATH).child(uuid).setValue(_tempFavoriteList).await()
            getUserFavorites()
        }
    }

    override fun deleteFromFavorites() {
        val uuid = authenticator.getActiveUser()?.uid.toString()
        viewModelScope.launch(Dispatchers.IO) {
            _tempFavoriteList.removeAll { it.title == currentStory?.title }
            dbRef.child(FAVORITES_DB_PATH).child(uuid).setValue(_tempFavoriteList).await()
            getUserFavorites()
        }
    }

    override fun getUserFavorites() {
        val uuid = authenticator.getActiveUser()?.uid.toString()
        viewModelScope.launch(Dispatchers.IO) {
            val snapshot = dbRef.child(FAVORITES_DB_PATH).child(uuid).get().await()
            val list = snapshot.children.map { it.getValue(StoryDto::class.java) }
            val mappedList = StoryMapper.fromDataToDomainModel(list)
            _tempFavoriteList.clear()
            _tempFavoriteList.addAll(mappedList)
            _favorites.postValue(_tempFavoriteList)
        }
    }
}