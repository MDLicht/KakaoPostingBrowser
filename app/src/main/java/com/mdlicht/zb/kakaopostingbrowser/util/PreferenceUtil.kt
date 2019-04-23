package com.mdlicht.zb.kakaopostingbrowser.util

import android.content.Context
import android.preference.PreferenceManager


object PreferenceUtil {
    const val KEY_SEARCH_HISTORY = "searchHistory"
    const val KEY_VISITED_HISTORY = "visitedHistory"

    fun getSearchHistory(context: Context): MutableSet<String> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getStringSet(KEY_SEARCH_HISTORY, mutableSetOf())!!
    }

    fun addSearchHistory(context: Context, item: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        val set = getSearchHistory(context)
        if(set.contains(item)) {
            set.remove(item)
        }
        set.add(item)
        editor.putStringSet(KEY_SEARCH_HISTORY, set)
        editor.apply()
    }

    fun getVisitedHistory(context: Context): MutableSet<String> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getStringSet(KEY_VISITED_HISTORY, mutableSetOf())!!
    }

    fun addVisitedHistory(context: Context, item: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        val set = getSearchHistory(context)
        if(set.contains(item)) {
            set.remove(item)
        }
        set.add(item)
        editor.putStringSet(KEY_VISITED_HISTORY, set)
        editor.apply()
    }

    fun isVisited(context: Context, item: String): Boolean {
        val set = getVisitedHistory(context)
        return set.contains(item)
    }
}