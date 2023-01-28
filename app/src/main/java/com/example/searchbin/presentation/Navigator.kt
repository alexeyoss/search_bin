package com.example.searchbin.presentation

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

fun Fragment.navigate(): Navigator = requireActivity() as Navigator

typealias  ResultListener<T> = (T) -> Unit

interface Navigator {
    fun launchScreen(screen: Fragment)
    fun goBack()
    fun <T : Parcelable> publishResult(data: T)
    fun <T : Parcelable> listenResult(
        clazz: Class<T>,
        owner: LifecycleOwner,
        listener: ResultListener<T>
    )
}