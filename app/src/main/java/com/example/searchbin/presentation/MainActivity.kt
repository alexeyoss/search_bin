package com.example.searchbin.presentation

import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.searchbin.R
import com.example.searchbin.data.db.entities.CachedBinInfoDTO
import com.example.searchbin.databinding.ActivityMainBinding
import com.example.searchbin.presentation.enter_bin_fragment.EnterBinFragment
import com.example.searchbin.presentation.request_history_fragment.RequestHistoryDetailsBottomSheet
import com.example.searchbin.presentation.request_history_fragment.RequestHistoryFragment
import com.example.searchbin.presentation.utils.UiUtil.replaceDataContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), Navigator {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.dataContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, RESTORE_KEY)
        } else {
            replaceDataContainer(EnterBinFragment())
        }

    }

    override fun launchScreen(screen: Fragment) {
        when (screen) {
            is RequestHistoryFragment -> replaceDataContainer(screen)
            is EnterBinFragment -> replaceDataContainer(screen)
            is RequestHistoryDetailsBottomSheet ->
                RequestHistoryDetailsBottomSheet().show(
                    supportFragmentManager, null
                )

        }
    }

    override fun goBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun <T : Parcelable> publishResult(data: T) {
        supportFragmentManager.setFragmentResult(
            data::class.java.name, bundleOf(RESULT_KEY to data)
        )
    }

    override fun <T : Parcelable> listenResult(
        clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>
    ) {
        supportFragmentManager.setFragmentResultListener(
            clazz.name,
            owner,
            FragmentResultListener { _, bundle ->
                listener.invoke(
                    bundle.getParcelable<CachedBinInfoDTO>(
                        RESULT_KEY,
                    ) as T
                )
            })
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        supportFragmentManager.putFragment(outState, RESTORE_KEY, currentFragment!!)
    }

    companion object {
        @JvmStatic
        private val RESULT_KEY = "RESULT_KEY"

        @JvmStatic
        val RESTORE_KEY = "RESTORE_KEY"
    }
}