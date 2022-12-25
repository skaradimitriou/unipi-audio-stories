package com.stathis.unipiaudiostories.ui.main.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.unipiaudiostories.abstraction.BaseViewHolder
import com.stathis.unipiaudiostories.abstraction.DiffUtilClass
import com.stathis.unipiaudiostories.abstraction.UiModel
import com.stathis.unipiaudiostories.databinding.HolderStatisticItemBinding
import com.stathis.unipiaudiostories.models.domain.StoryStatistic

class StatisticsAdapter : ListAdapter<UiModel, StatisticsViewHolder>(DiffUtilClass<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
        val view = HolderStatisticItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatisticsViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class StatisticsViewHolder(val binding: HolderStatisticItemBinding) : BaseViewHolder(binding) {

    override fun present(data: UiModel) {
        when (data) {
            is StoryStatistic -> binding.model = data
        }
    }
}