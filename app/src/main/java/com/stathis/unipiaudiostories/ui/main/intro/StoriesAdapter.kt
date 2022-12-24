package com.stathis.unipiaudiostories.ui.main.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.stathis.unipiaudiostories.abstraction.BaseViewHolder
import com.stathis.unipiaudiostories.abstraction.DiffUtilClass
import com.stathis.unipiaudiostories.abstraction.UiModel
import com.stathis.unipiaudiostories.databinding.HolderStoryItemBinding
import com.stathis.unipiaudiostories.models.domain.Story

class StoriesAdapter(private val callback: StoryCallback) :
    ListAdapter<UiModel, StoriesViewHolder>(DiffUtilClass<UiModel>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val view = HolderStoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class StoriesViewHolder(
    private val binding: HolderStoryItemBinding,
    private val callback: StoryCallback
) : BaseViewHolder(binding) {

    override fun present(data: UiModel) = when (data) {
        is Story -> {
            binding.model = data
            binding.callback = callback
        }
        else -> Unit
    }
}

fun interface StoryCallback {
    fun onStoryTap(story: Story)
}