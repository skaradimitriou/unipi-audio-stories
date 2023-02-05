package com.stathis.unipiaudiostories.ui.main.profile

import android.content.Intent
import android.provider.MediaStore
import androidx.fragment.app.viewModels
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentProfileBinding
import com.stathis.unipiaudiostories.ui.intro.IntroActivity
import com.stathis.unipiaudiostories.util.loadUserImage
import com.stathis.unipiaudiostories.util.onSuccessCameraResult
import com.stathis.unipiaudiostories.util.setScreenTitle
import com.stathis.unipiaudiostories.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()

    private val cameraIntent = onSuccessCameraResult { bitmap ->
        bitmap?.let {
            viewModel.uploadImage(bitmap)
        }
    }

    override fun init() {
        setScreenTitle(getString(R.string.menu_profile))
        binding.viewModel = viewModel
    }

    override fun startOps() {
        viewModel.getUserImage()
        viewModel.getUserData()

        binding.userImgView.setOnClickListener {
            askForCameraPhoto()
        }

        binding.logoutBtn.setOnClickListener {
            requireContext().showBottomSheet(
                title = getString(R.string.leaving),
                primaryBtnText = getString(R.string.logout),
                secondaryBtnText = getString(R.string.not_now),
                onButtonClick = { viewModel.logoutUser() }
            )
        }

        viewModel.userImg.observe(viewLifecycleOwner) { imgUrl ->
            binding.userImgView.loadUserImage(imgUrl)
        }

        viewModel.userLoggedOut.observe(viewLifecycleOwner) { loggedOut ->
            if (loggedOut) {
                startActivity(Intent(requireContext(), IntroActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun askForCameraPhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.launch(intent)
    }

    override fun stopOps() {}
}