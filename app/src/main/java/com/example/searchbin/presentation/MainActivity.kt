package com.example.searchbin.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.searchbin.R
import com.example.searchbin.databinding.ActivityMainBinding
import com.example.searchbin.presentation.enter_bin_fragment.EnterBinFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        supportFragmentManager.beginTransaction().replace(R.id.dataContainer, EnterBinFragment())
            .commit()
    }

}