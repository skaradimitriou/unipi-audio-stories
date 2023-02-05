package com.stathis.unipiaudiostories.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.*
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuProvider
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.databinding.LogoutBottomsheetBinding

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

fun Context.showBottomSheet(
    title: String,
    primaryBtnText: String,
    secondaryBtnText: String,
    onButtonClick: () -> Unit
) {
    val binding = LogoutBottomsheetBinding.inflate(LayoutInflater.from(this))
    val dialog = BottomSheetDialog(this)
    dialog.setContentView(binding.root)

    binding.titleTxtView.text = title
    binding.primaryBtn.text = primaryBtnText
    binding.secondaryBtn.text = secondaryBtnText

    binding.primaryBtn.setOnClickListener {
        onButtonClick.invoke()
        dialog.dismiss()
    }

    binding.secondaryBtn.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}

fun Fragment.onSuccessCameraResult(data: (Bitmap?) -> Unit) = registerForActivityResult(
    ActivityResultContracts.StartActivityForResult()
) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
        val bitmap = result.data?.extras?.get("data") as? Bitmap
        data.invoke(bitmap)
    }
}