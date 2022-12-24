package com.stathis.unipiaudiostories.util

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Helper method to set the screen title inside a [Fragment] in a more simple way.
 */

fun Fragment.setScreenTitle(title: String) {
    requireActivity().title = title
}

fun RecyclerView.setVerticalRecycler(topDimen: Int, horizontalDimen: Int) {
    setHasFixedSize(true)
    layoutManager = LinearLayoutManager(context)

    val decor = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.apply {
                this.top = resources.getDimensionPixelSize(topDimen)
                this.left = resources.getDimensionPixelSize(horizontalDimen)
                this.right = resources.getDimensionPixelSize(horizontalDimen)
            }
        }
    }
    addItemDecoration(decor)
}