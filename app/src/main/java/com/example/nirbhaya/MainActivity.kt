package com.example.nirbhaya

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nirbhaya.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> loadFragment(HomeFragment.newInstance())
                R.id.dashboard -> loadFragment(DashboardFragment.newInstance())
                R.id.guard -> loadFragment(GuardFragment.newInstance())
                R.id.profile -> loadFragment(ProfileFragment.newInstance())
            }
            true
        }

        // Load initial fragment
        loadFragment(HomeFragment.newInstance())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null) // Add to back stack for navigation
            .commit()
    }
}