package com.stathis.unipiaudiostories.ui.main.details

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentDetailsBinding
import com.stathis.unipiaudiostories.util.setScreenTitle
import timber.log.Timber


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val safeArgs : DetailsFragmentArgs by navArgs()

    override fun init() {
        //FIXME: Decide on screen title later on
        setScreenTitle("Ιστορία")
    }

    override fun startOps() {
        binding.model = safeArgs.story

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