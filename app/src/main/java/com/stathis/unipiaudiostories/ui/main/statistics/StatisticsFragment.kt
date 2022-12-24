package com.stathis.unipiaudiostories.ui.main.statistics

import androidx.fragment.app.viewModels
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentStatisticsBinding

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {

    private val viewModel: StatisticsViewModel by viewModels()

    override fun init() {
        requireActivity().title = "Statistics"
    }

    override fun startOps() {
        //
    }

    override fun stopOps() {
        //
    }
}