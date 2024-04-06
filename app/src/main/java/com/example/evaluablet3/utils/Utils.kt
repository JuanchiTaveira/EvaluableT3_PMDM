package com.example.evaluablet3.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

class Utils {
    companion object {
        fun hideKeyboard(fragment : Fragment, view: View) : Unit {
            val inputMethodManager = fragment.requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}