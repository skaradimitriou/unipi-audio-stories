package com.stathis.unipiaudiostories.ui.start

import android.content.Intent
import androidx.activity.viewModels
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseActivity
import com.stathis.unipiaudiostories.databinding.ActivityStartingBinding
import com.stathis.unipiaudiostories.ui.intro.IntroActivity
import com.stathis.unipiaudiostories.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartingActivity : BaseActivity<ActivityStartingBinding>(R.layout.activity_starting) {

    private val viewModel: StartingViewModel by viewModels()

    override fun init() {
        supportActionBar?.hide()
    }

    override fun startOps() {
        viewModel.getCurrentUser()
        viewModel.userIsLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                startActivity(Intent(this@StartingActivity, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@StartingActivity, IntroActivity::class.java))
                finish()
            }
        }
    }

    override fun stopOps() {}
}