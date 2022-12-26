package com.stathis.unipiaudiostories.ui.main.intro

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.models.repo.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val repo: StoryRepository,
    app: Application
) : BaseViewModel(app), StoryCallback {

    val adapter = StoriesAdapter(this)

    val stories: LiveData<List<Story>>
        get() = _stories

    private val _stories = MutableLiveData<List<Story>>()

    private lateinit var callback: StoryCallback

    fun getStories() {
        viewModelScope.launch {
            repo.getAllStories().collect {
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