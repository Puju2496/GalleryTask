package com.example.gallery

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import com.example.gallery.databinding.ActivityHomeBinding
import com.example.gallery.fragment.RecentPhotosFragment
import com.example.gallery.fragment.SearchFragment
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        setSupportActionBar(binding.toolBar)

        val drawerToggle = ActionBarDrawerToggle(this, binding.drawer, R.string.open, R.string.close)
        binding.drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        binding.toolBar.setNavigationOnClickListener {
            if (supportFragmentManager.findFragmentByTag(RECENT_PHOTO)?.isVisible == true) {
                binding.drawer.openDrawer(GravityCompat.START)
            } else {
                binding.toolBar.setNavigationIcon(R.drawable.navigation_icon)
                supportFragmentManager.commit {
                    replace(R.id.container, RecentPhotosFragment.newInstance(), RECENT_PHOTO)
                }
            }
        }
        binding.navigationView.setNavigationItemSelectedListener {
            binding.drawer.closeDrawer(GravityCompat.START)
            binding.toolBar.setNavigationIcon(R.drawable.back_icon)
            supportFragmentManager.commit {
                replace(R.id.container, SearchFragment.newInstance(), SEARCH)
            }
            return@setNavigationItemSelectedListener true
        }

        supportFragmentManager.commit {
            add(R.id.container, RecentPhotosFragment.newInstance(), RECENT_PHOTO)
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val RECENT_PHOTO = "RECENT_PHOTO"
        private const val SEARCH = "SEARCH"
    }
}