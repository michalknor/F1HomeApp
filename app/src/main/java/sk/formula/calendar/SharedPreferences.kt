package sk.formula.calendar

import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {

    private const val SETTINGS_PREFS_NAME = "settings"

    fun saveHost(context: Context, host: String, port: String) {
        context.getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putString("host", "$host:$port").apply()
    }

    fun getHost(context: Context): String? {
        val appContext = context.applicationContext
        val sharedPreferences: SharedPreferences =
            appContext.getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE)

        return sharedPreferences.getString("host", "host is not specified")
    }
}