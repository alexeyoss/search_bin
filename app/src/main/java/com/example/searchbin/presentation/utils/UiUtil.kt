package com.example.searchbin.presentation.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object UiUtil {

    fun hideKeyBoard(view: View) {
        val ims = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        ims.hideSoftInputFromWindow(view.windowToken, 0)
    }
}