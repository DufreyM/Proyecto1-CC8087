package com.example.waterreminder.achievements

import android.content.Context

fun saveAchievementStatus(context: Context, achievementName: String, unlocked: Boolean) {
    val sharedPreferences = context.getSharedPreferences("achievements", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean(achievementName, unlocked).apply()
}

fun getAchievementStatus(context: Context, achievementName: String): Boolean {
    val sharedPreferences = context.getSharedPreferences("achievements", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(achievementName, false)
}
