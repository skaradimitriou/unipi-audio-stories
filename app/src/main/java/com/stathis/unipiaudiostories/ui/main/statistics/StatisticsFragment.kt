package com.stathis.unipiaudiostories.ui.main.statistics

import androidx.fragment.app.viewModels
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentStatisticsBinding
import com.stathis.unipiaudiostories.util.setVerticalRecycler

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {

    private val viewModel: StatisticsViewModel by viewModels()

    override fun init() {
        requireActivity().title = getString(R.string.statistics_title)
        binding.viewModel = viewModel
        binding.storiesRecycler.setVerticalRecycler(
            topDimen = R.dimen.dimen_s,
            horizontalDimen = R.dimen.dimen_s
        )
    }

    override fun startOps() {
        viewModel.getStatistics()
        viewModel.observe(viewLifecycleOwner)
    }

    override fun stopOps() {}
}