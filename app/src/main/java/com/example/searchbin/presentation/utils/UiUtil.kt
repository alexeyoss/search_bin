package com.example.searchbin.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.searchbin.R

object UiUtil {

    fun hideKeyBoard(view: View) {
        val ims = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        ims.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun AppCompatActivity.replaceDataContainer(
        fragment: Fragment,
        addStack: Boolean = false,

        ) {
        if (addStack) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.navHostContainer, fragment, fragment.toString())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navHostContainer, fragment)
                .commit()
        }
    }
}