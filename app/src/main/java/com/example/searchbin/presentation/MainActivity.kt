package com.example.searchbin.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.searchbin.R
import com.example.searchbin.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navHost: NavHostFragment =
        NavHostFragment.create(R.navigation.nav_graph)


    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.navHostContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, RESTORE_KEY)
        } else {
            /** Manually install navHost component
             * Got issues with NavHost settings on XML side */
            supportFragmentManager.beginTransaction()
                .replace(R.id.navHostContainer, navHost)
                .setPrimaryNavigationFragment(navHost)
                .commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        supportFragmentManager.putFragment(outState, RESTORE_KEY, currentFragment!!)
    }

    companion object {
        @JvmStatic
        val RESTORE_KEY = "RESTORE_KEY"
    }
}