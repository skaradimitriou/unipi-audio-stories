package com.stathis.unipiaudiostories.ui.main.statistics

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.models.domain.StoryStatistic
import com.stathis.unipiaudiostories.models.repo.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repo: StoryRepository,
    app: Application
) : BaseViewModel(app) {

    val adapter = StatisticsAdapter()

    private val statistic: LiveData<List<StoryStatistic>>
        get() = _statistics

    private val _statistics = MutableLiveData<List<StoryStatistic>>()

    fun getStatistics() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getStoryStatistics().collect {
                _statistics.postValue(it)
            }
        }
    }

    fun observe(owner: LifecycleOwner) {
        statistic.observe(owner) {
            adapter.submitList(it)
        }
    }
}