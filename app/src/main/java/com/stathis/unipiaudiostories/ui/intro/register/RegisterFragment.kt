package com.stathis.unipiaudiostories.ui.intro.register

import android.content.Intent
import androidx.fragment.app.viewModels
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentRegisterBinding
import com.stathis.unipiaudiostories.ui.main.MainActivity


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun init() {}

    override fun startOps() {
        binding.registerBtn.setOnClickListener {
            val email = binding.emailInputField.text.toString()
            val pass = binding.passInputField.text.toString()
            val confirmPass = binding.passConfInputField.text.toString()

            viewModel.validateData(email, pass, confirmPass)
        }

        viewModel.userRegistered.observe(viewLifecycleOwner) { registered ->
            if (registered) goToDashboard()
            //FIXME: Handle else case later on
        }
    }

    override fun stopOps() {}

    private fun goToDashboard() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }
}