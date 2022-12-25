package com.stathis.unipiaudiostories.ui.main.statistics

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.models.domain.StoryStatistic
import com.stathis.unipiaudiostories.models.repo.StoryRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatisticsViewModel(app: Application) : BaseViewModel(app) {

    val adapter = StatisticsAdapter()

    private val statistic: LiveData<List<StoryStatistic>>
        get() = _statistics

    private val _statistics = MutableLiveData<List<StoryStatistic>>()

    private val repo = StoryRepositoryImpl(app)

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