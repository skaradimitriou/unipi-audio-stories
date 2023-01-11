package com.stathis.unipiaudiostories.ui.main.statistics

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.stathis.unipiaudiostories.abstraction.BaseViewModel
import com.stathis.unipiaudiostories.abstraction.UiModel
import com.stathis.unipiaudiostories.models.domain.StatisticHeader
import com.stathis.unipiaudiostories.models.repo.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    app: Application,
    private val repo: StoryRepository
) : BaseViewModel(app) {

    val adapter = StatisticsAdapter()

    val statistic: LiveData<List<UiModel>>
        get() = _statistics

    private val _statistics = MutableLiveData<List<UiModel>>()

    fun getStatistics() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getStoryStatistics().collect {
                val sum = it.sumOf { item -> item.counter }
                val header = StatisticHeader(sum)
                val list = mutableListOf<UiModel>().apply {
                    add(header)
                    addAll(it)
                }
                _statistics.postValue(list)
            }
        }
    }

    fun observe(owner: LifecycleOwner) {
        statistic.observe(owner) {
            adapter.submitList(it)
        }
    }
}