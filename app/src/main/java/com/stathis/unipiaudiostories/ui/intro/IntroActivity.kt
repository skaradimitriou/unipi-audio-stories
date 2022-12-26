package com.stathis.unipiaudiostories.ui.intro

import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseActivity
import com.stathis.unipiaudiostories.databinding.ActivityIntroBinding
import com.stathis.unipiaudiostories.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {

    private val viewModel: IntroViewModel by viewModels()
    private lateinit var navController: NavController

    override fun init() {
        navController = findNavController(R.id.nav_host_fragment)
        supportActionBar?.hide()
        viewModel.isUserActive()
    }

    override fun startOps() {
        viewModel.userActive.observe(this) { isActive ->
            if (isActive) goToDashboard()
        }
    }

    override fun stopOps() {}

    private fun goToDashboard() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}