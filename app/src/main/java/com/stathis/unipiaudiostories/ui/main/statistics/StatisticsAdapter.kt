package com.stathis.unipiaudiostories.ui.main.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.stathis.unipiaudiostories.BR
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseViewHolder
import com.stathis.unipiaudiostories.abstraction.DiffUtilClass
import com.stathis.unipiaudiostories.abstraction.UiModel
import com.stathis.unipiaudiostories.databinding.HolderEmptyItemBinding
import com.stathis.unipiaudiostories.databinding.HolderStatHeaderItemBinding
import com.stathis.unipiaudiostories.databinding.HolderStatisticItemBinding
import com.stathis.unipiaudiostories.models.domain.StatisticHeader
import com.stathis.unipiaudiostories.models.domain.StoryStatistic

class StatisticsAdapter : ListAdapter<UiModel, StatisticsViewHolder>(DiffUtilClass<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            R.layout.holder_statistic_item -> {
                HolderStatisticItemBinding.inflate(inflater, parent, false)
            }
            R.layout.holder_stat_header_item -> {
                HolderStatHeaderItemBinding.inflate(inflater, parent, false)
            }
            else -> HolderEmptyItemBinding.inflate(inflater, parent, false)
        }
        return StatisticsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is StatisticHeader -> R.layout.holder_stat_header_item
        is StoryStatistic -> R.layout.holder_statistic_item
        else -> R.layout.holder_empty_item
    }
}

class StatisticsViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is StatisticHeader -> binding.setVariable(BR.model, data)
            is StoryStatistic -> binding.setVariable(BR.model, data)
        }
    }
}