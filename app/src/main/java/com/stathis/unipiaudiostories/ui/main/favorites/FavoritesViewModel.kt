package com.stathis.unipiaudiostories.ui.main.favorites

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.repository.stories.StoryRepositoryImpl
import com.stathis.unipiaudiostories.ui.main.intro.StoriesAdapter
import com.stathis.unipiaudiostories.ui.main.intro.StoryCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    app: Application,
    private val repo: StoryRepositoryImpl
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
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllFavorites().collect { list ->
                _favorites.postValue(list)
            }
        }
    }

    override fun onStoryTap(story: Story) = callback.onStoryTap(story)
}