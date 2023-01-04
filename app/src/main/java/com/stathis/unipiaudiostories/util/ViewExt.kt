package com.stathis.unipiaudiostories.util

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

/**
 * Helper method to set the screen title inside a [Fragment] in a more simple way.
 */

fun Fragment.setScreenTitle(title: String) {
    requireActivity().title = title
}

fun ViewDataBinding.showSnackbar(message: String) {
    Snackbar.make(this.root, message, Snackbar.LENGTH_LONG).show()
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

/**
 * Helper method to set a custom Menu inside a Fragment.
 * @param menuId : The menu id reference that will be inflated.
 * @param onMenuCreated : Callback in case the user needs a reference to the menu
 * @param onItemSelected : Callback for user clicks on menu items.
 */

fun Fragment.setMenuProvider(
    menuId: Int,
    onMenuCreated: (Menu) -> Unit,
    onItemSelected: (MenuItem) -> Unit
) {
    requireActivity().addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(menuId, menu)
            onMenuCreated.invoke(menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            onItemSelected.invoke(menuItem)
            return false
        }
    }, viewLifecycleOwner)
}

/**
 * Helper method to simplify the procedure of getting a drawable inside a fragment.
 */

fun Fragment.getDrawable(drawableId: Int): Drawable? {
    return requireContext().getAppDrawable(drawableId)
}