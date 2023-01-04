package com.stathis.unipiaudiostories.ui.main.details

import android.view.Menu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentDetailsBinding
import com.stathis.unipiaudiostories.util.getAppropriateIcon
import com.stathis.unipiaudiostories.util.getDrawable
import com.stathis.unipiaudiostories.util.setMenuProvider
import com.stathis.unipiaudiostories.util.setScreenTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val safeArgs: DetailsFragmentArgs by navArgs()

    private lateinit var menu: Menu

    override fun init() {
        binding.model = safeArgs.story
        viewModel.currentStory = safeArgs.story

        setScreenTitle(safeArgs.story.title)
        setMenuProvider(
            menuId = R.menu.story_details_menu,
            onMenuCreated = {
                menu = it
            },
            onItemSelected = {
                viewModel.favoriteIconClicked()
            }
        )
    }

    override fun startOps() {
        binding.playStoryBtn.setOnClickListener {
            goToPlayNowScreen()
        }

        viewModel.incrementCounterOnDb(safeArgs.story.title)
        viewModel.observe(viewLifecycleOwner)
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            val drawable = getAppropriateIcon(isFavorite)
            if (::menu.isInitialized) menu.getItem(0).icon = getDrawable(drawable)
        }
    }

    override fun stopOps() {}

    private fun goToPlayNowScreen() {
        val action = DetailsFragmentDirections.goToPlayStoryScreen(safeArgs.story)
        findNavController().navigate(action)
    }
}