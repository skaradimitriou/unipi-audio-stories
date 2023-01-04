package com.stathis.unipiaudiostories.ui.main.favorites

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentFavoritesBinding
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.util.setScreenTitle
import com.stathis.unipiaudiostories.util.setVerticalRecycler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModels()

    override fun init() {
        setScreenTitle(getString(R.string.favorites))
        binding.viewModel = viewModel
        binding.storiesRecycler.setVerticalRecycler(
            topDimen = R.dimen.dimen_s,
            horizontalDimen = R.dimen.dimen_s
        )
    }

    override fun startOps() {
        viewModel.getUserFavorites()
        viewModel.observe(viewLifecycleOwner) { selectedStory ->
            goToDetails(selectedStory)
        }
    }

    override fun stopOps() {}

    private fun goToDetails(story: Story) {
        val action = FavoritesFragmentDirections.goToDetails(story)
        findNavController().navigate(action)
    }
}