package com.stathis.unipiaudiostories.ui.main.details

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentDetailsBinding
import com.stathis.unipiaudiostories.util.setScreenTitle


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val safeArgs: DetailsFragmentArgs by navArgs()

    override fun init() {
        binding.model = safeArgs.story
        setScreenTitle(safeArgs.story.title)
    }

    override fun startOps() {
        viewModel.incrementCounterOnDb(safeArgs.story.title)

        binding.playStoryBtn.setOnClickListener {
            goToPlayNowScreen()
        }
    }

    override fun stopOps() {}

    private fun goToPlayNowScreen() {
        val action = DetailsFragmentDirections.goToPlayStoryScreen(safeArgs.story)
        findNavController().navigate(action)
    }
}