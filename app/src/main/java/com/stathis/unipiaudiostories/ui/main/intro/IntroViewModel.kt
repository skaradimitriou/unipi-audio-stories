package com.stathis.unipiaudiostories.ui.main.intro

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.repo.StoryRepositoryImpl
import kotlinx.coroutines.launch

class IntroViewModel(app: Application) : BaseViewModel(app), StoryCallback {

    private val _repo = StoryRepositoryImpl(app)
    val adapter = StoriesAdapter(this)

    val stories: LiveData<List<Story>>
        get() = _stories

    private val _stories = MutableLiveData<List<Story>>()

    private lateinit var callback: StoryCallback

    fun getStories() {
        viewModelScope.launch {
            _repo.getAllStories().collect {
                _stories.postValue(it)
            }
        }
    }

    fun observe(owner: LifecycleOwner, callback: StoryCallback) {
        this.callback = callback
        stories.observe(owner) { list ->
            adapter.submitList(list)
        }
    }

    override fun onStoryTap(story: Story) = callback.onStoryTap(story)
}