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
            binding.drawer.openDrawer(GravityCompat.START)
        }
        binding.navigationView.setNavigationItemSelectedListener {
            binding.drawer.closeDrawer(GravityCompat.START)
            supportFragmentManager.commit {
                replace(R.id.container, SearchFragment.newInstance())
            }
            return@setNavigationItemSelectedListener true
        }

        supportFragmentManager.commit {
            add(R.id.container, RecentPhotosFragment.newInstance())
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }
}