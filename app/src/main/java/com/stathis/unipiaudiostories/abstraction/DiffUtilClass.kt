package com.stathis.unipiaudiostories.abstraction

import androidx.recyclerview.widget.DiffUtil

abstract class DiffUtilClass<T : UiModel> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.equalsContent(newItem)
    }
}