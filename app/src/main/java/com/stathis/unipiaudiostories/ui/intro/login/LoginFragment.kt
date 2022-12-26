package com.stathis.unipiaudiostories.ui.intro.login

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentLoginBinding
import com.stathis.unipiaudiostories.models.domain.Result
import com.stathis.unipiaudiostories.ui.main.MainActivity
import com.stathis.unipiaudiostories.util.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun init() {}

    override fun startOps() {
        binding.loginBtn.setOnClickListener {
            val email = binding.emailInputField.text.toString()
            val pass = binding.passInputField.text.toString()

            viewModel.login(email, pass)
        }

        binding.registerBtn.setOnClickListener {
            goToRegisterScreen()
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> goToDashboard()
                is Result.Failure -> binding.showSnackbar(result.message)
            }
        }
    }

    override fun stopOps() {}

    private fun goToDashboard() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
        requireActivity().finish()
    }

    private fun goToRegisterScreen() {
        val action = LoginFragmentDirections.goToRegister()
        findNavController().navigate(action)
    }
}