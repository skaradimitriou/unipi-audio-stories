package com.stathis.unipiaudiostories.ui.intro.login

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseFragment
import com.stathis.unipiaudiostories.databinding.FragmentLoginBinding


class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun init() {}

    override fun startOps() {
        binding.registerBtn.setOnClickListener {
            goToRegisterScreen()
        }
    }

    override fun stopOps() {}

    private fun goToRegisterScreen() {
        val action = LoginFragmentDirections.goToRegister()
        findNavController().navigate(action)
    }
}