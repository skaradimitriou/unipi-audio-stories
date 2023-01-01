package com.stathis.unipiaudiostories.ui.main.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentProfileBinding
import com.stathis.unipiaudiostories.ui.intro.IntroActivity
import com.stathis.unipiaudiostories.util.setScreenTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun init() {
        setScreenTitle(getString(R.string.menu_profile))
        binding.viewModel = viewModel
    }

    override fun startOps() {
        viewModel.getUserData()

        viewModel.userLoggedOut.observe(viewLifecycleOwner) { loggedOut ->
            if (loggedOut) {
                startActivity(Intent(requireContext(), IntroActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    override fun stopOps() {}
}