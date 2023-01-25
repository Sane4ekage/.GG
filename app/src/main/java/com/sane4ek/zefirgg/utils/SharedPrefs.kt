package com.sane4ek.zefirgg.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sane4ek.zefirgg.core.data.UserDataModel

object SharedPrefs {


    private const val APP_PREFERENCES = "zefirggprefs"

    fun saveStringPrefs(key: String?, value: String?, context: Context) {
        val sharedpreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringPrefs(key: String?, context: Context): String? {
        val sharedpreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        return sharedpreferences.getString(key, null)
    }

    fun saveBooleanPrefs(key: String?, value: Boolean, context: Context) {
        val sharedpreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBooleanPrefs(key: String?, context: Context): Boolean {
        val sharedpreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        return sharedpreferences.getBoolean(key, false)
    }

    fun saveUserDataModel(key: String?, value: UserDataModel, context: Context) {
        val sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(value)
        editor.putString(key, json)
        editor.apply()
    }

    fun getUserDataModel(key: String, context: Context): UserDataModel {
        val mPrefs: SharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val strObject = mPrefs.getString(key, "")
        val type = object : TypeToken<UserDataModel>() {}.type
        return Gson().fromJson(strObject, type)
    }

}