package com.example.productshopping

import android.content.Context
import android.content.SharedPreferences


object Constant {
    private const val PREF_NAME = "myeshop_prefs"
    private const val USER_LOGGED_IN = "user_logged_in"
    private const val USER_EMAIL = "user_email"


    fun saveUserLoginStatus(context: Context, isLoggedIn: Boolean, email: String?) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean(USER_LOGGED_IN, isLoggedIn)
        email?.let { editor.putString(USER_EMAIL, it) }
        editor.apply()
    }

    fun isUserLoggedIn(context: Context): Boolean {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(USER_LOGGED_IN, false)
    }

}
