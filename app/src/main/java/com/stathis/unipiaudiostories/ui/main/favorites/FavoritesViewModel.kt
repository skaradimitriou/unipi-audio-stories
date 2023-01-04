package com.stathis.unipiaudiostories.ui.main.favorites

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DatabaseReference
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.models.data.StoryDto
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.mapper.StoryMapper
import com.stathis.unipiaudiostories.ui.main.intro.StoriesAdapter
import com.stathis.unipiaudiostories.ui.main.intro.StoryCallback
import com.stathis.unipiaudiostories.util.FAVORITES_DB_PATH
import com.stathis.unipiaudiostories.util.authmanager.Authenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    app: Application,
    private val authenticator: Authenticator,
    private val dbRef: DatabaseReference
) : BaseViewModel(app), StoryCallback {

    val favorites: LiveData<List<Story>>
        get() = _favorites

    private val _favorites = MutableLiveData<List<Story>>()

    val adapter = StoriesAdapter(this)

    private lateinit var callback: StoryCallback

    fun observe(owner: LifecycleOwner, callback: StoryCallback) {
        this.callback = callback
        favorites.observe(owner) {
            adapter.submitList(it)
        }
    }

    fun getUserFavorites() {
        val uuid = authenticator.getActiveUser()?.uid.toString()
        viewModelScope.launch(Dispatchers.IO) {
            val snapshot = dbRef.child(FAVORITES_DB_PATH).child(uuid).get().await()
            val list = snapshot.children.map { it.getValue(StoryDto::class.java) }
            val mappedList = StoryMapper.fromDataToDomainModel(list)
            _favorites.postValue(mappedList)
        }
    }

    override fun onStoryTap(story: Story) = callback.onStoryTap(story)
}