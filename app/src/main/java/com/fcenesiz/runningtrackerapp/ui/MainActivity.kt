package com.fcenesiz.runningtrackerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fcenesiz.runningtrackerapp.R
import com.fcenesiz.runningtrackerapp.databinding.ActivityMainBinding
import com.fcenesiz.runningtrackerapp.db.RunDAO
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        public val TAG: String = MainActivity::class.simpleName.toString()
    }

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.apply {
            setContentView(root)
            setSupportActionBar(toolbar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
            bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
            navHostFragment.findNavController()
                .addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        R.id.settingsFragment, R.id.runFragment, R.id.staticticsFragment ->
                            bottomNavigationView.visibility = View.VISIBLE
                        else -> bottomNavigationView.visibility = View.GONE
                    }
                }
        }

    }
}