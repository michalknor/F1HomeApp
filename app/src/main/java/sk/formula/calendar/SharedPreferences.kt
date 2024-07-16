package sk.formula.calendar

import android.content.Context
import android.content.SharedPreferences

object SharedPreferences {

    private const val SETTINGS_PREFS_NAME = "settings"

    fun saveHost(context: Context, ip: String, port: String) {
        context.getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putString("ip", "$ip").putString("port", "$port").apply()
    }

    fun getIp(context: Context): String {
        return context.applicationContext.getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE).getString("ip", "").toString()
    }

    fun getPort(context: Context): String {
        return context.applicationContext.getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE).getString("port", "").toString()
    }

    fun getHost(context: Context): String {
        return getIp(context) + ":" + getPort(context)
    }
}