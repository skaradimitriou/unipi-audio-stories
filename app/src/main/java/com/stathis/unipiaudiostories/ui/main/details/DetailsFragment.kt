package com.stathis.unipiaudiostories.ui.main.details

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentDetailsBinding
import com.stathis.unipiaudiostories.util.setScreenTitle


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val safeArgs : DetailsFragmentArgs by navArgs()

    override fun init() {
        //FIXME: Decide on screen title later on
        setScreenTitle("Ιστορία")
    }

    override fun startOps() {
        binding.model = safeArgs.story
    }

    override fun stopOps() {
        //
    }
}