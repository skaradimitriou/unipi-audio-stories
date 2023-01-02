package com.stathis.unipiaudiostories.ui.main.intro

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentIntroBinding
import com.stathis.unipiaudiostories.models.domain.Story
import com.stathis.unipiaudiostories.util.setScreenTitle
import com.stathis.unipiaudiostories.util.setVerticalRecycler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : BaseFragment<FragmentIntroBinding>(R.layout.fragment_intro) {

    private val viewModel: IntroViewModel by viewModels()

    override fun init() {
        setScreenTitle(getString(R.string.stories_title))

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.storiesRecycler.setVerticalRecycler(
            topDimen = R.dimen.dimen_s,
            horizontalDimen = R.dimen.dimen_s
        )
    }

    override fun startOps() {
        viewModel.getStoriesFromDb()

        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getStoriesFromDb()
        }

        viewModel.stories.observe(viewLifecycleOwner) { list ->
            binding.noResultsLayout.showEmptyResult = list.isEmpty()
            binding.swipeToRefresh.isRefreshing = false
        }

        viewModel.observe(this) { selectedStory ->
            goToDetails(story = selectedStory)
        }
    }

    override fun stopOps() {}

    private fun goToDetails(story: Story) {
        val action = IntroFragmentDirections.goToDetails(story)
        findNavController().navigate(action)
    }
}