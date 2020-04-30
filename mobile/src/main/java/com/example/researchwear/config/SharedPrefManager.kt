package com.example.researchwear.config

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {
    private val PREF_NAME = "WomenSafetyApp"
    private val IS_LOGGED_IN = "IsLoggedIn"
    private val USER_ID = "userID"
    private val IS_REQUIRED_PERMISSION_GRANTED = "IsPermissionGranted"

    private var preference: SharedPreferences? = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor? = preference?.edit()

    fun createLoginSession(userId: String){
        editor?.putBoolean(IS_LOGGED_IN, true)
        editor?.putString(USER_ID, userId)
        editor?.commit()
    }

    fun getUserMno(): String?{
        return preference?.getString(USER_ID, null)
    }

    fun isLoggedIn(): Boolean? {
        return preference?.getBoolean(IS_LOGGED_IN, false)
    }

    fun isRequiredPermissionGranted(): Boolean?{
        return preference?.getBoolean(IS_REQUIRED_PERMISSION_GRANTED, false)
    }

    fun setAllPermissionGranted(){
        editor?.putBoolean(IS_REQUIRED_PERMISSION_GRANTED, true)
        editor?.commit()
    }

    fun clearLoginSession(){
        editor?.clear()
        editor?.commit()
    }
}