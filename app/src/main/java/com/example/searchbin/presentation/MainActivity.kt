package com.example.searchbin.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.searchbin.R
import com.example.searchbin.databinding.ActivityMainBinding
import com.example.searchbin.presentation.enter_bin_fragment.EnterBinFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navController by lazy {
        val navHost = supportFragmentManager.findFragmentById(R.id.dataContainer) as NavHostFragment
        navHost.navController
    }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}