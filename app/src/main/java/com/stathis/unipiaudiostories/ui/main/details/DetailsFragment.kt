package com.stathis.unipiaudiostories.ui.main.details

import android.view.Menu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentDetailsBinding
import com.stathis.unipiaudiostories.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val safeArgs: DetailsFragmentArgs by navArgs()

    private var menu: Menu? = null

    override fun init() {
        binding.viewModel = viewModel
        binding.model = safeArgs.story
        viewModel.currentStory = safeArgs.story

        viewModel.extractTextFromImage(safeArgs.story.image)
        setScreenTitle(safeArgs.story.title)
    }

    override fun startOps() {
        setMenuProvider(
            menuId = R.menu.story_details_menu,
            onMenuCreated = { menu = it },
            onItemSelected = { viewModel.favoriteIconClicked() }
        )

        binding.playStoryBtn.setOnClickListener {
            goToPlayNowScreen()
        }

        viewModel.observe(viewLifecycleOwner)
        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            val drawable = getAppropriateIcon(isFavorite)
            menu?.getItemOrNull(0)?.apply {
                icon = getDrawable(drawable)
            }
        }
    }

    override fun stopOps() {}

    private fun goToPlayNowScreen() {
        val action = DetailsFragmentDirections.goToPlayStoryScreen(safeArgs.story)
        findNavController().navigate(action)
    }
}