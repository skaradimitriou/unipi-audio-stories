package com.stathis.unipiaudiostories.ui.main

import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.stathis.unipiaudiostories.R
import com.stathis.unipiaudiostories.abstraction.BaseActivity
import com.stathis.unipiaudiostories.databinding.ActivityMainBinding
import com.stathis.unipiaudiostories.util.showBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navController: NavController

    override fun init() {
        navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationMenu.setupWithNavController(navController)
    }

    override fun startOps() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.nav_home || destination.id == R.id.nav_favorites || destination.id == R.id.nav_statistics || destination.id == R.id.nav_profile) {
                //disable action bar back button
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

        binding.bottomNavigationMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> navController.popBackStack(R.id.nav_home, false)
                R.id.nav_favorites -> navController.navigate(R.id.nav_favorites)
                R.id.nav_statistics -> navController.navigate(R.id.nav_statistics)
                R.id.nav_profile -> navController.navigate(R.id.nav_profile)
            }
            false
        }
    }

    override fun stopOps() {}

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            if (navController.graph.startDestinationId == navController.currentDestination?.id) {
                finish()
            } else {
                navController.popBackStack()
            }
            true
        }
        else -> false
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.nav_home) {
            showBottomSheet(
                title = getString(R.string.leaving),
                primaryBtnText = getString(R.string.exit),
                secondaryBtnText = getString(R.string.not_now),
                onButtonClick = { super.onBackPressed() }
            )
        } else {
            super.onBackPressed()
        }
    }
}